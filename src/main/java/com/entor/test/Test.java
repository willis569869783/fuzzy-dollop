package com.entor.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
//import redis.clients.jedis.ListPosition;

public class Test {
	public static void main(String[] args) {
		/*************** Spring字符串 ******************/
		Jedis jedis = new Jedis("localhost");
		jedis.set("foo", "张珊");
		String value = jedis.get("foo");
		System.out.println(value);
		jedis.del("foo");
		value = jedis.get("foo");
		System.out.println(value);

		jedis.mset("name", "张珊", "sex", "女", "age", "22");

		jedis.append("name", "22");
		List<String> list = jedis.mget("name", "sex", "age");
		System.out.println(list);
		/******************* Hash *************************/
		Map<String, String> map = new HashMap<>();
		map.put("name", "李四");
		map.put("sex", "男");
		map.put("age", "20");

		jedis.hmset("map", map);

		list = jedis.hmget("map", "name", "sex", "age");
		System.out.println(list);
		Set<String> set = jedis.hkeys("map");
		System.out.println(set);
		list = jedis.hvals("map");
		System.out.println(list);
		jedis.del("map");

		/******************* list *************************/
		// 列表左侧添加数据
		jedis.del("names");
		jedis.lpush("names", "的手机");
		jedis.lpush("names", "啥都的");
		jedis.lpush("names", "睡觉觉");
		jedis.lpush("names", "张珊", "李四", "王五", "赵柳");
		// 列表右侧添加数据
		jedis.rpush("names", "弟弟", "妹妹", "姐姐");
		System.out.println(jedis.lrange("names", 0, -1));
		// 获取列表长度
		System.out.println(jedis.llen("names"));
		// 获取指定
		System.out.println(jedis.lindex("names", 2));
		// 删除左边第一个元素,返回该元素值
		System.out.println(jedis.lpop("names"));
		// 删除右边第一个元素,返回该元素值
		System.out.println(jedis.rpop("names"));
		System.out.println(jedis.lrange("names", 0, -1));

		// 在列表左边第一个元素插入数据
//		jedis.linsert("names", ListPosition.AFTER, "妹妹", "AA");
		System.out.println(jedis.lrange("names", 0, -1));

		// 删除所有的王五
		jedis.lrem("names", 0, "王五");
		// 删除左边王五
//		jedis.lrem("name", 1, "王五");
		// 删除右边王五
//		jedis.lrem("name", -1, "王五");
		System.out.println(jedis.lrange("names", 0, -1));
		/******************* Set *************************/
		// 添加元素
		jedis.sadd("sname", "meepo");
		jedis.sadd("sname", "dota");
		jedis.sadd("sname", "poofu");
		jedis.sadd("sname", "noname");
		// 移除noname,可以删除多个
		jedis.srem("sname", "noname");
		// 获取所有加入的元素
		System.out.println(jedis.smembers("sname"));
		// 判断 meepo 是否是sname集合的元素
		System.out.println(jedis.sismember("sname", "meepo"));
		// 随机返回一个集合元素
		System.out.println(jedis.srandmember("sname"));
		// 随机取出(移除)一个元素
		System.out.println(jedis.spop("sname"));
		System.out.println(jedis.smembers("sname"));
		// 返回集合的元素个数
		System.out.println(jedis.scard("sname"));
		jedis.close();
	}
}
