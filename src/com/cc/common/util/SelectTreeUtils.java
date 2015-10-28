package com.cc.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.springframework.util.Assert;

import com.cc.common.orm.ISelectTree;


@SuppressWarnings({"rawtypes","unchecked"})
public class SelectTreeUtils {
	public static List webTree(List<? extends ISelectTree> list) {
		Assert.notNull(list);
		Generator gen = new Generator(list);
		return gen.generate();
	}
	
	public static List handleTreeChild(List<? extends ISelectTree> list) {
		Assert.notNull(list);
		List<ISelectTree> roots = new ArrayList<ISelectTree>();
		ISelectTree p;
		Set child;
		for (ISelectTree n : list) {
			p = n.getTreeParent();
			if (p == null || p.getId() == null || !list.contains(p)) {
				roots.add(n);
			} else {
				child = p.getTreeChildRaw();
				if (child == null) {
					child = new LinkedHashSet<ISelectTree>();
					p.setTreeChild(child);
				}
				child.add(n);
			}
		}
		return roots;
	}

	private static class Generator {
		private List<? extends ISelectTree>	roots;

		public Generator(List<? extends ISelectTree> roots) {
			this.roots = roots;
		}
		/**
		 * 生成树
		 */
		public List<? extends ISelectTree> generate() {
			List<ISelectTree> container = new ArrayList<ISelectTree>();
			Stack<Boolean> isEndList = new Stack<Boolean>();
			isEndList.add(new Boolean(true));
			for (ISelectTree o : roots) {
				container = node(container, o, 0, isEndList);
			}
			return container;
		}
		private List<ISelectTree> node(List<ISelectTree> container, ISelectTree node, int deep, Stack<Boolean> isEndList) {
			if (container == null) {
				container = new ArrayList<ISelectTree>();
			}
			StringBuilder sb = new StringBuilder();
			// 空格列
			if (deep >= 1) {
				sb.append("　");
			}
			// 线条列
			for (int i = 1; i < deep; i++) {
				if (!isEndList.get(i)) {
					sb.append("│");
				} else {
					sb.append("　");
				}
			}
			// 节点列
			if (deep == 0) {
				// 父节点
				// sb.append("♀");
			} else if (isEndList.get(deep)) {
				sb.append("└");
			} else {
				sb.append("├");
			}
			sb.append(node.getTreeName());
			node.setSelectTree(sb.toString());
			container.add(node);
			// 子节点
			Set<? extends ISelectTree> cld = node.getTreeChild();
			if (cld != null) {
				for (Iterator<? extends ISelectTree> it = cld.iterator(); it.hasNext();) {
					ISelectTree o = it.next();
					// 入栈
					isEndList.push(!it.hasNext());
					node(container, o, deep + 1, isEndList);
					// 出栈
					isEndList.pop();
				}
			}
			return container;
		}
	}
}
