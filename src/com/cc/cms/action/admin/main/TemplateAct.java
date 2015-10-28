
package com.cc.cms.action.admin.main;

import static com.cc.cms.Constants.$_PAGE;
import static com.cc.cms.Constants.$_RES;
import static com.cc.cms.Constants.$_RESTEMPLATE;
import static com.cc.cms.Constants.FRONT;
import static com.cc.cms.Constants.RES;
import static com.cc.cms.Constants.TEMPLATE;
import static com.cc.common.Constants.FILE_SPT;
import static com.cc.common.Constants.SPT;
import static com.cc.common.Constants.UTF8;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import com.cc.cms.action.CmsAct;
import com.cc.cms.entity.main.Template;
import com.cc.cms.service.main.ITemplateSvc;
import com.cc.common.orm.hibernate3.OrderBy;
import com.cc.common.util.FileWrap;

/** @author wangcheng */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Scope("prototype")
@Controller("web.action.admin.templateAct")
public class TemplateAct extends CmsAct {

	private static final long		serialVersionUID	= 5844031901716879344L;
	private static final Logger		log					= LoggerFactory.getLogger(TemplateAct.class);
	private FileWrap				treeRoot;
	private String					relPath;
	private List<FileWrap>			tplDirList;
	private String					parentPath;
	private String					tplContent;
	private String					tplName;
	private String					dirName;
	private String					origName;
	private List<FileWrap>			subDir;
	private File					tplsFile;
	private String					tplsFileContentType;
	private String					tplsFileFileName;
	private Map<String, String[]>	dirMap;
	private InputStream				inputStream;
	private int						uploadRuleId;
	@Autowired
	private ITemplateSvc			templateSvc;
	private Template				template;
	private String					error;

	@Override
	public String main() throws Exception {
		if (contextPvd.getActionContext().getParameters().get("type") != null) {
			setSrcLeft("template_fileleft");
			setSrcRight("template_filelist");
		}
		return super.main();
	}

	private void initList() {
		list = templateSvc.findAll(new OrderBy[] { OrderBy.asc("priority") });
	}

	@Override
	public String list() {
		initList();
		return LIST;
	}

	@Override
	public String add() {
		initTplDir();
		addUploadRule();
		return ADD;
	}

	private void initTplDir() {
		// 设置模板列表
		String path = contextPvd.getAppRealPath(getWeb().getTplPath().toString());
		File dir = new File(path);
		tplDirList = new FileWrap(dir).getChild();
	}

	public String save() {
		templateSvc.saveTemplate(template);
		websiteSvc.handleWebsiteChange();
		return list();
	}

	public boolean validateSave() {
		if (hasErrors()) {
			initTplDir();
			log.error("保存模板时出现action/field错误");
			addActionError("保存模板时出现错误");
			return true;
		}
		return false;
	}

	@Override
	public String edit() {
		template = templateSvc.findById(id);
		initTplDir();
		addUploadRule();
		return EDIT;
	}

	public String update() {
		template = (Template) templateSvc.updateDefault(template);
		websiteSvc.handleWebsiteChange();
		return list();
	}

	public boolean validateUpdate() {
		if (hasErrors()) {
			initTplDir();
			log.error("修改模板时出现action/field错误");
			addActionError("修改模板时出现错误");
			return true;
		}
		return false;
	}

	public String delete() {
		try {
			for (Template bean : templateSvc.deleteById(ids)) {
				log.info("删除  模板 成功:{}", bean.getName());
			}
			addActionMessage("成功删除模板 ");
		} catch (DataIntegrityViolationException e) {
			addActionError("记录已被引用，不能删除!");
			log.error("删除站点失败,记录被引用");
		}
		setId(null);
		return list();
	}

	public boolean validateDelete() {
		if (hasErrors()) {
			log.error("发生action/field错误");
			return true;
		}
		if (vldBatch()) {
			log.error("删除ID不能为空");
			return true;
		}
		for (Integer id : ids) {
			if (vldExist(id)) {
				return true;
			}
		}
		return false;
	}

	private boolean vldExist(Integer id) {
		Template entity = templateSvc.findById(id);
		if (entity == null) {
			addActionError("记录不存在：" + id);
			log.error("记录不存在{}", id);
			return true;
		}
		return false;
	}

	// /////////////////////////////////////////////
	// ///////文件管理
	// ////////////////////////////////////////////
	public String fileleft() {
		String path = getWeb().getTplRootReal(contextPvd.getAppRoot()).toString().replace(SPT, FILE_SPT);
		if (getWeb().getRank() != 1) {
			path = getWeb().getTplRootReal(contextPvd.getAppRoot()).append("\\user\\").append(getWeb().getDomain()).toString().replace(SPT, FILE_SPT);
		}
		File tplFile = new File(path);
		treeRoot = new FileWrap(tplFile, path, new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.getName().startsWith(".") || f.getName().startsWith("$")) {
					return false;
				} else {
					return true;
				}
			}
		});
		return "fileleft";
	}

	private void initFileList() {
		if (relPath == null) {
			relPath = "";
		}
		String path = contextPvd.getAppRealPath(getWeb().getTplRoot().append(relPath).toString());
		if (getWeb().getRank() != 1) {
			path = contextPvd.getAppRealPath(getWeb().getTplRoot().append("\\user\\").append(getWeb().getDomain()).append(relPath).toString());
		}
		File dir = new File(path);
		subDir = new FileWrap(dir).getChild();
		if (relPath.indexOf("\\") != -1) {
			relPath = relPath.replace("\\", "\\\\");
		}
	}

	public String filelist() {
		initFileList();
		return "filelist";
	}

	public String fileadd() {
		return "fileadd";
	}

	public String filesave() {
		String path = contextPvd.getAppRealPath(getWeb().getTplRoot().toString());
		if (saveFile(path)) {
			addActionMessage("添加模板文件成功");
			log.debug("添加模板文件成功");
			setDefResult("filelist.jsp");
			initFileList();
			return DEFRESULT;
		}
		addActionMessage("添加模板文件失败");
		log.debug("添加模板文件失败");
		return INPUT;
	}

	private boolean saveFile(String path) {
		File tpl = new File(path + relPath + FILE_SPT + tplName);
		try {
			FileUtils.writeStringToFile(tpl, tplContent, UTF8);
			return true;
		} catch (IOException e) {
			log.error("写入模板失败！", e);
			addActionError("写入模板失败！");
			return false;
		}
	}

	private void editFile(String path) {
		File tpl = new File(path);
		tplName = tpl.getName();
		parentPath = relPath.substring(0, relPath.lastIndexOf(FILE_SPT));
		try {
			tplContent = FileUtils.readFileToString(tpl, UTF8);
		} catch (IOException e) {
			log.error("读取模板文件失败", e);
			addActionError("读取模板文件失败!");
		}
	}

	public String fileedit() {
		String path = contextPvd.getAppRealPath(getWeb().getTplRoot().append(relPath).toString());
		editFile(path);
		return "fileedit";
	}

	public String fileupdate() throws JSONException {
		String path = contextPvd.getAppRealPath(getWeb().getTplRoot().append(relPath).toString());
		if (updateFile(path)) {
			log.debug("修改文件成功");
			addActionMessage("修改文件成功");
			return JSON;
		}
		log.debug("修改文件失败");
		addActionMessage("修改文件失败");
		return JSON;
	}

	private boolean updateFile(String path) throws JSONException {
		File tpl = new File(path);
		File newFile = tpl;
		if (!tpl.getName().equals(tplName)) {
			newFile = new File(tpl.getParent() + FILE_SPT + tplName);
			tpl.renameTo(newFile);
		}
		try {
			FileUtils.writeStringToFile(newFile, tplContent, UTF8);
			getJsonRoot().put("success", true);
			getJsonRoot().put("msg", "保存成功");
			log.debug("写文件成功");
			return true;
		} catch (IOException e) {
			log.error("写文件失败", e);
			getJsonRoot().put("success", false);
			getJsonRoot().put("msg", "写文件失败！");
			return false;
		}
	}

	private void renameFile(String path) throws JSONException {
		File tpl = new File(path);
		if (!origName.equals(tplName)) {
			boolean b = tpl.renameTo(new File(tpl.getParent() + FILE_SPT + tplName));
			if (!b) {
				getJsonRoot().put("success", false);
				log.error("重命名失败");
			} else {
				getJsonRoot().put("success", true);
				log.debug("重命名成功");
			}
		} else {
			log.debug("名称没有改变");
			getJsonRoot().put("success", new Object());
		}
	}

	public String rename() throws JSONException {
		String path = contextPvd.getAppRealPath(getWeb().getTplRoot().append(relPath).append(SPT).append(origName).toString());
		renameFile(path);
		return JSON;
	}

	public String deleteFile() {
		String path = contextPvd.getAppRealPath(getWeb().getTplRoot().append(relPath).append(SPT).append(tplName).toString());
		File tpl = new File(path);
		if (FileUtils.deleteQuietly(tpl)) {
			addActionMessage("删除模板文件成功！");
		} else {
			addActionError("删除模板文件失败！");
		}
		return filelist();
	}

	public String createDir() {
		String path = contextPvd.getAppRealPath(getWeb().getTplRoot().append(relPath).append(SPT).append(dirName).toString());
		File dir = new File(path);
		dir.mkdir();
		addActionMessage("成功创建文件夹");
		return filelist();
	}

	public String importTpl() {
		return "importTpl";
	}

	public String importTplSubmit() {
		setDefResult("importTplIframe.html");
		if (tplsFile == null) {
			error = ("请选择要上传的文件！");
			return DEFRESULT_FREEMARKER;
		}
		if (!tplsFileFileName.toLowerCase().endsWith(".zip")) {
			error = ("请使用zip格式的模板压缩包！");
			return DEFRESULT_FREEMARKER;
		}
		initTplDir();
		String tplPath = contextPvd.getAppRealPath(getWeb().getTplPath().toString());
		String resPath = contextPvd.getAppRealPath(SPT + RES + SPT + FRONT);
		String resTplPath = contextPvd.getAppRealPath(SPT + RES + SPT + TEMPLATE);
		try {
			ZipFile zip = new ZipFile(tplsFile);
			Enumeration<ZipEntry> en = zip.getEntries();
			// 判断压缩文件目录格式是否合法、系统中是否存在重复模板
			Enumeration<ZipEntry> ten = zip.getEntries();
			while (ten.hasMoreElements()) {
				ZipEntry entry = ten.nextElement();
				if (!entry.isDirectory()) {
					String name = entry.getName();
					if (name.indexOf("/") == -1
							|| (!name.contains($_RES) && !name.contains($_PAGE) && !name.contains($_RESTEMPLATE))
							|| name.startsWith($_RES)
							|| name.startsWith($_PAGE)
							|| name.startsWith($_RESTEMPLATE)
							|| (!name.substring(name.indexOf("/") + 1).startsWith($_RESTEMPLATE)
									&& !name.substring(name.indexOf("/") + 1).startsWith($_RES) && !name.substring(name.indexOf("/") + 1).startsWith(
									$_PAGE))) {
						error = ("压缩文件目录格式不正确,请按照提示建立目录结构！");
						log.error("压缩文件目录格式不正确,请按照提示建立目录结构！");
						return DEFRESULT_FREEMARKER;
					} else {
						tplsFileFileName = name.substring(0, name.indexOf("/"));
					}
				}
			}
			for (FileWrap fileWrap : tplDirList) {
				if (fileWrap.getName().equals(tplsFileFileName)) {
					error = ("系统中已存在此名称的模板，请重新命名文件！");
					log.error("系统中已存在此名称的模板，请重新命名文件！");
					return DEFRESULT_FREEMARKER;
				}
			}
			ZipEntry entry = null;
			String name = null;
			// String sFileName = null;
			String fileName = null;
			File outFile = null;
			File pfile = null;
			byte[] buf = new byte[1024];
			int len = 0;
			InputStream is = null;
			OutputStream os = null;
			while (en.hasMoreElements()) {
				entry = en.nextElement();
				if (!entry.isDirectory()) {
					name = entry.getName();
					log.debug("解压文件：{}", name);
					// 判断是页面文件还是资源文件
					// boolean isRes = false;
					if (name.indexOf($_RES) != -1) {
						// isRes = true;
						// 先写入系统路径，再写入站点路径
						// sFileName = resPath + SPT + name.replace($_RES + SPT, "");
						fileName = resPath + SPT + name.replace($_RES + SPT, "");
					} else if (name.indexOf($_RESTEMPLATE) != -1) {
						// isRes = true;
						// 先写入系统路径，再写入站点路径
						// sFileName = resTplPath + SPT + name.replace($_RESTEMPLATE + SPT, "");
						fileName = resTplPath + SPT + name.replace($_RESTEMPLATE + SPT, "");
					} else {
						// 页面文件
						fileName = tplPath + SPT + name.replace($_PAGE + SPT, "");
					}
					// if (name.indexOf($_RES) != -1) {
					// isRes = true;
					// // 先写入系统路径，再写入站点路径
					// sFileName = resTplPath + SPT + name.replace($_RES + SPT, "");
					// fileName = resPath + SPT + name.replace($_RES + SPT, "");
					// } else {
					// // 页面文件
					// fileName = tplPath + SPT + name.replace($_PAGE + SPT, "");
					// }
					// 若是资源文件，写入系统路径
					// if (isRes) {
					// sFileName = sFileName.replace(SPT, FILE_SPT);
					// log.debug("解压地址：{}", sFileName);
					// outFile = new File(sFileName);
					// pfile = outFile.getParentFile();
					// if (!pfile.exists()) {
					// pfile.mkdirs();
					// }
					// try {
					// is = zip.getInputStream(entry);
					// os = new FileOutputStream(outFile);
					// while ((len = is.read(buf)) != -1) {
					// os.write(buf, 0, len);
					// }
					// } finally {
					// if (is != null) {
					// is.close();
					// is = null;
					// }
					// if (os != null) {
					// os.close();
					// os = null;
					// }
					// }
					// }
					fileName = fileName.replace(SPT, FILE_SPT);
					// log.debug("解压地址：{}", fileName);
					outFile = new File(fileName);
					pfile = outFile.getParentFile();
					if (!pfile.exists()) {
						pfile.mkdirs();
					}
					try {
						is = zip.getInputStream(entry);
						os = new FileOutputStream(outFile);
						while ((len = is.read(buf)) != -1) {
							os.write(buf, 0, len);
						}
					} finally {
						if (is != null) {
							is.close();
							is = null;
						}
						if (os != null) {
							os.close();
							os = null;
						}
					}
				}
			}
			log.debug("导入模板成功");
		} catch (IOException e) {
			log.error("导入模板时IO错误！", e);
			error = ("导入模板时发生读写错误！");
			return DEFRESULT_FREEMARKER;
		}
		return DEFRESULT_FREEMARKER;
	}

	public String disable() {
		template = templateSvc.findById(id);
		template.setIsDisabled(true);
		templateSvc.update(template);
		addActionMessage("成功禁用模板");
		setId(null);
		return list();
	}

	public String enable() {
		template = templateSvc.findById(id);
		template.setIsDisabled(false);
		templateSvc.update(template);
		addActionMessage("成功启用模板");
		setId(null);
		return list();
	}

	/** 排序 是否可向上移动
	 * 
	 * @param id
	 * @return */
	public boolean isUp(Template bean) {
		return templateSvc.isUp(bean);
	}

	public boolean isDown(Template bean) {
		return templateSvc.isDown(bean);
	}

	public String up() {
		templateSvc.up(id);
		addActionMessage("排序成功");
		setId(null);
		return list();
	}

	public boolean validateUp() {
		if (hasErrors()) {
			log.error("发生action/field错误");
			initList();
			addActionError("发生错误");
			return true;
		}
		// if (!isUp(templateSvc.findById(id))) {
		// addActionError("不能向上移动");
		// return true;
		// }
		return false;
	}

	public String down() {
		templateSvc.down(id);
		addActionMessage("排序成功");
		setId(null);
		return list();
	}

	public boolean validateDown() {
		if (hasErrors()) {
			log.error("发生action/field错误");
			initList();
			addActionError("发生错误");
			return true;
		}
		// if (!isDown(templateSvc.findById(id))) {
		// addActionError("不能向下移动");
		// return true;
		// }
		return false;
	}

	public FileWrap getTreeRoot() {
		return treeRoot;
	}

	public void setTreeRoot(FileWrap treeRoot) {
		this.treeRoot = treeRoot;
	}

	public String getTplContent() {
		return tplContent;
	}

	public void setTplContent(String tplContent) {
		this.tplContent = tplContent;
	}

	public String getTplName() {
		return tplName;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	public String getRelPath() {
		return relPath;
	}

	public void setRelPath(String relPath) {
		this.relPath = relPath;
	}

	public List<FileWrap> getTplDirList() {
		return tplDirList;
	}

	public void setTplDirList(List<FileWrap> tplDirList) {
		this.tplDirList = tplDirList;
	}

	public List<FileWrap> getSubDir() {
		return subDir;
	}

	public void setSubDir(List<FileWrap> subDir) {
		this.subDir = subDir;
	}

	public String getOrigName() {
		return origName;
	}

	public void setOrigName(String origName) {
		this.origName = origName;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	public Map<String, String[]> getDirMap() {
		return dirMap;
	}

	public void setDirMap(Map<String, String[]> dirMap) {
		this.dirMap = dirMap;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public File getTplsFile() {
		return tplsFile;
	}

	public void setTplsFile(File tplsFile) {
		this.tplsFile = tplsFile;
	}

	public String getTplsFileContentType() {
		return tplsFileContentType;
	}

	public void setTplsFileContentType(String tplsFileContentType) {
		this.tplsFileContentType = tplsFileContentType;
	}

	public String getTplsFileFileName() {
		return tplsFileFileName;
	}

	public void setTplsFileFileName(String tplsFileFileName) {
		this.tplsFileFileName = tplsFileFileName;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public int getUploadRuleId() {
		return uploadRuleId;
	}

	@Override
	public void setUploadRuleId(int uploadRuleId) {
		this.uploadRuleId = uploadRuleId;
	}
}
