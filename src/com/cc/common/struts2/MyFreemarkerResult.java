package com.cc.common.struts2;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.apache.struts2.views.util.ResourceUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class MyFreemarkerResult implements Result, StrutsStatics {
	private static final long	serialVersionUID	= 2824963114666563643L;
	public static final String	PARSE_LOCATION		= "ftlPath";
	/** The default parameter */
	public static final String	DEFAULT_PARAM		= "location";
	protected ActionInvocation	invocation;
	protected Configuration		configuration;
	protected ObjectWrapper		wrapper;
	protected FreemarkerManager	freemarkerManager;
	private Writer				writer;
	private String				pContentType		= "text/html";
	protected String			location;

	public MyFreemarkerResult() {
		this(null);
	}
	public MyFreemarkerResult(String location) {
		this.location = location;
	}
	@Inject
	public void setFreemarkerManager(FreemarkerManager mgr) {
		this.freemarkerManager = mgr;
	}
	public void setContentType(String aContentType) {
		pContentType = aContentType;
	}
	/**
	 * allow parameterization of the contentType the default being text/html
	 */
	public String getContentType() {
		return pContentType;
	}
	@Override
	public void execute(ActionInvocation invocation) throws Exception {
		this.invocation = invocation;
		this.configuration = getConfiguration();
		this.wrapper = getObjectWrapper();
		parseLocation();
		Template template = configuration.getTemplate(location, deduceLocale());
		TemplateModel model = createModel();
		// Give subclasses a chance to hook into preprocessing
		if (preTemplateProcess(template, model)) {
			try {
				// Process the template
				template.process(model, getWriter());
			} finally {
				// Give subclasses a chance to hook into postprocessing
				postTemplateProcess(template, model);
			}
		}
	}
	private void parseLocation() {
		if (PARSE_LOCATION.equals(location)) {
			location = invocation.getStack().findString(PARSE_LOCATION);
		} else if (!location.startsWith("/")) {
			ActionContext ctx = invocation.getInvocationContext();
			HttpServletRequest req = (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
			String base = ResourceUtil.getResourceBase(req);
			location = base + "/" + location;
		}
	}
	/**
	 * This method is called from {@link #doExecute(String, ActionInvocation)} to obtain the FreeMarker configuration
	 * object that this result will use
	 * for template loading. This is a hook that allows you to custom-configure
	 * the configuration object in a subclass, or to fetch it from an IoC
	 * container.
	 * <p/>
	 * <b> The default implementation obtains the configuration from the ConfigurationManager instance. </b>
	 */
	protected Configuration getConfiguration() throws TemplateException {
		return freemarkerManager.getConfiguration(ServletActionContext.getServletContext());
	}
	/**
	 * This method is called from {@link #doExecute(String, ActionInvocation)} to obtain the FreeMarker object wrapper
	 * object that this result will use
	 * for adapting objects into template models. This is a hook that allows you
	 * to custom-configure the wrapper object in a subclass.
	 * <p/>
	 * <b> The default implementation returns {@link Configuration#getObjectWrapper()} </b>
	 */
	protected ObjectWrapper getObjectWrapper() {
		return configuration.getObjectWrapper();
	}
	public void setWriter(Writer writer) {
		this.writer = writer;
	}
	/**
	 * The default writer writes directly to the response writer.
	 */
	protected Writer getWriter() throws IOException {
		if (writer != null) {
			return writer;
		}
		return ServletActionContext.getResponse().getWriter();
	}
	/**
	 * Build the instance of the ScopesHashModel, including JspTagLib support
	 * <p/>
	 * Objects added to the model are
	 * <p/>
	 * <ul>
	 * <li>Application - servlet context attributes hash model
	 * <li>JspTaglibs - jsp tag lib factory model
	 * <li>Request - request attributes hash model
	 * <li>Session - session attributes hash model
	 * <li>request - the HttpServletRequst object for direct access
	 * <li>response - the HttpServletResponse object for direct access
	 * <li>stack - the OgnLValueStack instance for direct access
	 * <li>ognl - the instance of the OgnlTool
	 * <li>action - the action itself
	 * <li>exception - optional : the JSP or Servlet exception as per the servlet spec (for JSP Exception pages)
	 * <li>struts - instance of the StrutsUtil class
	 * </ul>
	 */
	protected TemplateModel createModel() throws TemplateModelException {
		ServletContext servletContext = ServletActionContext.getServletContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		ValueStack stack = ActionContext.getContext().getValueStack();
		Object action = null;
		if (invocation != null) {
			// Added for NullPointException
			action = invocation.getAction();
		}
		return freemarkerManager.buildTemplateModel(stack, action, servletContext, request, response, wrapper);
	}
	/**
	 * Returns the locale used for the {@link Configuration#getTemplate(String, Locale)} call. The base
	 * implementation simply returns the locale setting of the action (assuming
	 * the action implements {@link LocaleProvider}) or, if the action does not
	 * the configuration's locale is returned. Override this method to provide
	 * different behaviour,
	 */
	protected Locale deduceLocale() {
		if (invocation.getAction() instanceof LocaleProvider) {
			return ((LocaleProvider) invocation.getAction()).getLocale();
		} else {
			return configuration.getLocale();
		}
	}
	/**
	 * the default implementation of postTemplateProcess applies the contentType
	 * parameter
	 */
	protected void postTemplateProcess(Template template, TemplateModel data) throws IOException {}
	/**
	 * Called before the execution is passed to template.process(). This is a
	 * generic hook you might use in subclasses to perform a specific action
	 * before the template is processed. By default does nothing. A typical
	 * action to perform here is to inject application-specific objects into the
	 * model root
	 * 
	 * @return true to process the template, false to suppress template
	 *         processing.
	 */
	protected boolean preTemplateProcess(Template template, TemplateModel model) throws IOException {
		Object attrContentType = template.getCustomAttribute("content_type");
		if (attrContentType != null) {
			ServletActionContext.getResponse().setContentType(attrContentType.toString());
		} else {
			String contentType = getContentType();
			if (contentType == null) {
				contentType = "text/html";
			}
			String encoding = template.getEncoding();
			if (encoding != null) {
				contentType = contentType + "; charset=" + encoding;
			}
			ServletActionContext.getResponse().setContentType(contentType);
		}
		return true;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
