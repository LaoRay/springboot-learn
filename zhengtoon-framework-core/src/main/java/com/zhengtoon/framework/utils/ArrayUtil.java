package com.zhengtoon.framework.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArrayUtil
{

	public static String[] removeBlank(String[] originalArray) throws RuntimeException
	{

		if (originalArray == null)
		{
			return null;
		}
		else if (originalArray.length == 0)
		{
			return null;
		}
		else
		{
			List<String> tempList = new ArrayList<String>();
			for (int i = 0; i < originalArray.length; i++)
			{
				if (StringUtils.isNotBlank(originalArray[i]))
				{
					tempList.add(originalArray[i].trim());
				}
			}
			String[] result = new String[tempList.size()];
			for (int j = 0; j < tempList.size(); j++)
			{
				result[j] = tempList.get(j);
			}
			return result;
		}
	}

	public static List<List<String>> splitAryToList(String[] ary, int subSize)
	{
		int count = ary.length % subSize == 0 ? ary.length / subSize : ary.length / subSize + 1;

		List<List<String>> subAryList = new ArrayList<List<String>>();

		for (int i = 0; i < count; i++)
		{
			int index = i * subSize;
			List<String> list = new ArrayList<String>();
			int j = 0;
			while (j < subSize && index < ary.length)
			{
				list.add(ary[index++]);
				j++;
			}
			subAryList.add(list);
		}
		return subAryList;
	}
    
    public static List<List<String>> splitList(List<String> allList, int subSize) {
        int count = allList.size() % subSize == 0 ? allList.size() / subSize : allList.size() / subSize + 1;
        
        List<List<String>> subAryList = new ArrayList<List<String>>();
        
        for (int i = 0; i < count; i++) {
            int index = i * subSize;
            List<String> list = new ArrayList<String>();
            int j = 0;
            while (j < subSize && index < allList.size()) {
                list.add(allList.get(index++));
                j++;
            }
            subAryList.add(list);
        }
        return subAryList;
    }

	public static List removeNull(List sourceList) throws RuntimeException
	{
		if (sourceList == null)
		{
			return null;
		}
		else if (sourceList.size() == 0)
		{
			return null;
		}
		else
		{
			List result = new ArrayList();
			for (int i = 0; i < sourceList.size(); i++)
			{
				if (sourceList.get(i) != null)
				{
					result.add(sourceList.get(i));
				}
			}
			return result;
		}
	}

	public static List<String> removeDuplicate(List<String> sourceList) throws RuntimeException {
		Set<String> strSet = new HashSet<String>();
		strSet.addAll(sourceList);
		List<String> result = new ArrayList<String>();
		for(String str:strSet){
			result.add(str);
		}
		return result;
	}
}
