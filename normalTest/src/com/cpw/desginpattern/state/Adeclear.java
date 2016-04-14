package com.cpw.desginpattern.state;

public class Adeclear {
	/**
	 * 也是为了消灭if。。else。。同CoR一样
	 * 以红绿灯为例子：红灯状态-->绿灯-->黄灯
	 * 一般的，会：if(light.color==red)next=yellow elseif()...
	 * 严重不符合open-close原则
	 * 所以：同CoR将每一种分支封装成一个类：RedState，YellowState，BlueState
	 * 都继承自State，有handlePush/handlePull以及getColor
	 * Context其实就是state manager 聚合了state。
	 * 在各自的handle里面Context.getState(new XXState())，那么就能找到自己的next了
	 * 
	 */
}
