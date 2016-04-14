package com.cpw.desginpatten.flyweight;

public class Adeclear {
	/**
	 * 享元模式，flyweight
	 * 背景：处理大量重复出现数据的，如:员工和manager，员工类里面manager属性，是为每个员工的manager属性new 一个manager吗?显然不是，数据量太大了。
	 * 发现manager张三，是很多的manager，干脆事先就把张三放到一个pool中，当创建一个员工的时候，发现张三就是他的manager， 直接去pool里面提取，
	 * 这个张三就是和共享的元。
	 * 类似的例子还有：将一个xml文件转成java 对象的时候，发现默写属性重复，大量出现，就提取出来，比如：
	 * 
<?xml version="1.0"?>
<collection>

<cd>
<title>Another Green World</title>
<year>1978</year>
<artist>Eno, Brian</artist>
</cd>

<cd>
<title>Greatest Hits</title>
<year>1950</year>
<artist>Holiday, Billie</artist>
</cd>

<cd>
<title>Taking Tiger Mountain (by strategy)</title>
<year>1977</year>
<artist>Eno, Brian</artist>
</cd>

....... 

</collection>

cd的artist里面Eno，Brian重复出现了，我们就把他放到pool里面，用得时候，直接取，而不是位每个节点（cd）创建一个对象

	 */

}
