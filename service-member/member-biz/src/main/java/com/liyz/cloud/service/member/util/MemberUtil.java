package com.liyz.cloud.service.member.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.liyz.cloud.common.model.constant.common.CommonConstant.EMAILREG;
import static com.liyz.cloud.common.model.constant.common.CommonConstant.PHONEREG;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/12 10:06
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MemberUtil {

    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    /**
     * 匹配手机
     *
     * @param mobile
     * @return
     */
    public static boolean matchMobile(String mobile) {
        Pattern p = Pattern.compile(PHONEREG);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 匹配邮箱
     *
     * @param email
     * @return
     */
    public static boolean matchEmail(String email) {
        Pattern p = Pattern.compile(EMAILREG);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 获取随机数字
     *
     * @param length 随机数长度
     * @return
     */
    public static String randomInteger(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
