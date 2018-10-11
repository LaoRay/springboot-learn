package com.zhengtoon.framework.utils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

/**
 * Utility class for generating random Strings.
 */
public final class RandomUtil
{

	private static final int DEF_COUNT = 20;
    private static final String letterStr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    private RandomUtil()
	{
	}

	/**
	 * Generates a password.
	 *
	 * @return the generated password
	 */
	public static String generatePassword()
	{
		return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
	}

	/**
	 * Generates an activation key.
	 *
	 * @return the generated activation key
	 */
	public static String generateActivationKey()
	{
		return RandomStringUtils.randomNumeric(DEF_COUNT);
	}

	public static String generateAccidKey()
	{
		return RandomStringUtils.random(4, "0123456789");
	}

	public static String generateFileNum()
	{
		return RandomStringUtils.random(3, "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPGRSTUVWXYZ");
	}

	public static int generateInteger(int max)
	{
		return RandomUtils.nextInt(max);
	}

	public static Integer[] random(Integer size, Integer max)
	{
		Set<Integer> indexSet = new HashSet<Integer>();
		for (int i = 0; i < size; i++)
		{
			Integer index = RandomUtils.nextInt(max);
			while (indexSet.contains(index))
			{
				index = RandomUtils.nextInt(max);
			}
			indexSet.add(index);
		}
		return indexSet.toArray(new Integer[size]);
	}

	/**
	 * 37.381673, -122.031119 中心 37.381673, -122.041119 向左移动
	 * 37.391673,-122.031119 向上移动 37.381673, -122.011119 向右移动
	 * 37.371673,-122.031119 向下移动
	 *
	 * @param lat
	 * @param lng
	 * @param amount
	 * @return
	 */
	public static Double[][] generateLatLng(Double lat, Double lng, Integer amount, Integer max, Integer min)
	{
		if (amount < 4)
			amount = 4;
		Double[][] latlngs = new Double[amount][2];
		for (int i = 0; i < amount; i++)
		{
			Double randomLat = randomDouble(max, min, 1000000.00);
			Double randomLng = randomDouble(max, min, 1000000.00);
			// 第一象限(+,+)
			if ((i + 1) % 4 == 1)
			{
				latlngs[i][0] = lat + randomLat;
				latlngs[i][1] = lng + randomLng;
			}
			// 第二象限(-,+)
			else if ((i + 1) % 4 == 2)
			{
				latlngs[i][0] = lat - randomLat;
				latlngs[i][1] = lng + randomLng;
			}
			// 第三象限(-,-)
			else if ((i + 1) % 4 == 3)
			{
				latlngs[i][0] = lat - randomLat;
				latlngs[i][1] = lng - randomLng;
			}
			// 第四象限(+,-)
			else
			{
				latlngs[i][0] = lat + randomLat;
				latlngs[i][1] = lng - randomLng;
			}
		}
		return latlngs;
	}

	public static Double randomDouble(int max, int min, double seed)
	{
		int s = randomInt(max, min);
		Double result = (s * 1.0 / seed);
		return result;
	}

	public static Integer randomInt(int max, int min)
	{
		return RandomUtils.nextInt(max) % (max - min + 1) + min;
	}

	public static String generatePhoneNumber()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(RandomStringUtils.random(1, "123456789")).append(RandomStringUtils.random(9, "0123456789"));
		return sb.toString();
	}

	public static String randomNumbers()
	{
		String amt = RandomStringUtils.random(1, "5678");
		return RandomStringUtils.random(Integer.valueOf(amt), "0123456789");
	}

	public static String randomALetter()
	{
		return RandomStringUtils.random(1, "aaaaabc");
	}

	public static String randomANumber()
	{
		return RandomStringUtils.random(1, "0123456789");
	}

	public static String randomConnector()
	{
		Boolean flag = RandomUtils.nextBoolean();
		if (flag)
		{
			return "";
		}
		else
		{
			return RandomStringUtils.random(1, "_-.");
		}
	}

	public static String randomANumbers()
	{
		return RandomStringUtils.random(RandomUtils.nextInt(9), "0123456789");
	}

	public static String randomLetters()
	{
		int num = RandomUtils.nextInt(3);
		while (num == 0)
		{
			num = RandomUtils.nextInt(3);
		}
		return RandomStringUtils.random(num, "abcdefghijklmnopqrstuvwxyz");
	}

	public static String randomANum()
	{
		return RandomStringUtils.random(1, "123456789");
	}

	public static Integer randomComLen()
	{
		return Integer.valueOf(RandomStringUtils.random(1, "122233"));
	}

	public static String randomType()
	{
		return RandomStringUtils.random(1, "abcde");
	}

	public static Long rendomDistance(Long distance)
	{
		if (distance >= 50)
			return distance;
		else
		{
			if (distance > 0 && distance <= 5)
			{
				return BigDecimal.valueOf(distance * 1.1).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
			}
			else if (distance > 5 && distance <= 10)
			{
				return BigDecimal.valueOf(distance * 1.2).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
			}
			else if (distance > 10 && distance <= 15)
			{
				return BigDecimal.valueOf(distance * 1.3).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
			}
			else if (distance > 15 && distance <= 20)
			{
				return BigDecimal.valueOf(distance * 1.4).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
			}
			else if (distance > 20 && distance <= 25)
			{
				return BigDecimal.valueOf(distance * 1.5).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
			}
			else if (distance > 25 && distance <= 30)
			{
				return BigDecimal.valueOf(distance * 1.6).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
			}
			else if (distance > 30 && distance <= 35)
			{
				return BigDecimal.valueOf(distance * 1.7).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
			}
			else if (distance > 35 && distance <= 40)
			{
				return BigDecimal.valueOf(distance * 1.8).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
			}
			else if (distance > 40 && distance <= 45)
			{
				return BigDecimal.valueOf(distance * 1.3).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
			}
			else if (distance > 45 && distance <= 50)
			{
				return BigDecimal.valueOf(distance * 1.5).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
			}
			else
			{
				return distance;
			}
		}
	}

    public static Long randomLong(Long min, Long max){
        return org.apache.commons.lang3.RandomUtils.nextLong(min,max);
    }
    
    //add by qindaorong 20180305 #5356 start
    /**
     * 产生一个随机的字母字符串
     *
     * @param length 随即字符串位数
     */
    public static String RandomString(int length) {
        return RandomStringUtils.random(length, letterStr);
    }
    //add by qindaorong 20180305 #5356 end
    
    public static Integer getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
