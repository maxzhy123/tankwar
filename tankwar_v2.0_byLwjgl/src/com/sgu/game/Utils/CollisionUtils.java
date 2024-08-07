
package com.sgu.game.Utils;

import com.sgu.game.entity.BulletElement;
import com.sgu.game.entity.Element;
import com.sgu.game.entity.TankElement;

public class CollisionUtils {
	private CollisionUtils() {

	}

	/**
	 * 判断两个矩形是否碰撞
	 *
	 * @param x1
	 *            第一个矩形的 x坐标
	 * @param y1
	 *            第一个矩形的 y坐标
	 * @param w1
	 *            第一个矩形的 宽度
	 * @param h1
	 *            第一个矩形的 高度
	 * @param x2
	 *            第二个矩形的 x坐标
	 * @param y2
	 *            第二个矩形的 y坐标
	 * @param w2
	 *            第二个矩形的 宽度
	 * @param h2
	 *            第二个矩形的 高度
	 */
	public static boolean isCollisionWithRect(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
		if (x1 >= x2 && x1 >= x2 + w2) {
			return false;
		} else if (x1 <= x2 && x1 + w1 <= x2) {
			return false;
		} else if (y1 >= y2 && y1 >= y2 + h2) {
			return false;
		} else if (y1 <= y2 && y1 + h1 <= y2) {
			return false;
		}
		return true;
	}
}
