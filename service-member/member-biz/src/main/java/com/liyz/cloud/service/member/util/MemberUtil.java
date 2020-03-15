package com.liyz.cloud.service.member.util;

import com.liyz.cloud.common.model.constant.member.MemberServiceCodeEnum;
import com.liyz.cloud.common.model.exception.mmeber.RemoteMemberServiceException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    /**
     * 校验地址是否是邮件或者手机号码格式，如果不是，则抛出异常
     *
     * @param address
     * @param codeEnum
     * @return 1：手机号码；2：邮件
     */
    public static int checkMobileEmail(String address, MemberServiceCodeEnum codeEnum) {
        int type;
        if (MemberUtil.matchMobile(address)) {
            type = 1;
        } else if (MemberUtil.matchEmail(address)){
            type = 2;
        } else {
            throw new RemoteMemberServiceException(codeEnum);
        }
        return type;
    }

    /**
     * 利用MD5进行加密
     *
     * @param plainText 待加密的字符串
     * @return 加密后的字符串
     * @throws NoSuchAlgorithmException     没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException
     */
    public static String encodeMD5(String plainText) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] secretBytes;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        // 16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    public static void main(String[] args) {
        String baseStr = "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";
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            byte[] b = decoder.decodeBuffer(baseStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0){
                    b[i]+=256;
                }
            }
            String imgFilePath = "C:\\Users\\yangzhen.li\\Desktop\\base.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
