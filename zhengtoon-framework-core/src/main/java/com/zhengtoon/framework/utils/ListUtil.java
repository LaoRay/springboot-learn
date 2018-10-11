package com.zhengtoon.framework.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtil
{
	public static Object[] splitListToArr(List<String> strList, int subSize)
	{
		int count = strList.size() % subSize == 0 ? strList.size() / subSize : strList.size() / subSize + 1;
		List<List<String>> subAryList = new ArrayList<List<String>>();
		for (int i = 0; i < count; i++)
		{
			int index = i * subSize;
			List<String> list = new ArrayList<String>();
			int j = 0;
			while (j < subSize && index < strList.size())
			{
				list.add(strList.get(index++));
				j++;
			}
			subAryList.add(list);
		}
		Object[] subAry = new Object[subAryList.size()];

		for (int i = 0; i < subAryList.size(); i++)
		{
			List<String> subList = subAryList.get(i);
			String[] subAryItem = new String[subList.size()];
			for (int j = 0; j < subList.size(); j++)
			{
				subAryItem[j] = subList.get(j);
			}
			subAry[i] = subAryItem;
		}
		return subAry;
	}
    
    public static List getPageList(List list, int pageSize) {
        List<List> resultList = new ArrayList<List>();
        //page to push
        int totalCount = list.size();
        int pageCount = 0;
        int surplusNum = totalCount % pageSize;
        if (surplusNum > 0) {
            pageCount = totalCount / pageSize + 1;
        } else {
            pageCount = totalCount / pageSize;
        }
        for (int i = 1; i <= pageCount; i++) {
            List subList = new ArrayList<>();
            
            if (surplusNum == 0) {
                subList.addAll(list.subList((i - 1) * pageSize, pageSize * (i)));
            } else {
                if (i == pageCount) {
                    subList.addAll(list.subList((i - 1) * pageSize, totalCount));
                } else {
                    subList.addAll(list.subList((i - 1) * pageSize, pageSize * (i)));
                }
            }
            resultList.add(subList);
        }
        return resultList;
    }
    
    public static List splitToLists(List sourceList, int amount) {
        if (null == sourceList || sourceList.size() == 0 || amount <= 0) {
            throw new RuntimeException("wrong parameter");
        }
        List<List> result = new ArrayList<List>();
        double count = sourceList.size() / amount;
        int perListCount = new Double(count).intValue() + 1;
        if (sourceList.size() <= amount) {
            result.add(sourceList);
        } else {
            for (int i = 0; i < amount; i++) {
                List subList = new ArrayList();
                if (i == (amount - 1)) {
                    int start = i * perListCount;
                    int end = sourceList.size() == 0 ? 1 : sourceList.size();
                    if (start >= sourceList.size() && end >= sourceList.size()) {
                        break;
                    } else if (start < sourceList.size() && end >= sourceList.size()) {
                        end = sourceList.size();
                    }
                    subList.addAll(sourceList.subList(start, end));
                } else {
                    int start = i * perListCount;
                    int end = (i + 1) * perListCount == 0 ? 1 : (i + 1) * perListCount;
                    if (start >= sourceList.size() && end >= sourceList.size()) {
                        break;
                    } else if (start < sourceList.size() && end >= sourceList.size()) {
                        end = sourceList.size();
                    }
                    subList.addAll(sourceList.subList(start, end));
                }
                result.add(subList);
            }
        }
        
        return result;
    }
}
