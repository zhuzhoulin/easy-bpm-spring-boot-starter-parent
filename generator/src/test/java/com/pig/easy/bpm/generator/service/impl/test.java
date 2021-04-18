package com.pig.easy.bpm.generator.service.impl;

import java.math.BigDecimal;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/3/12 16:33
 */
public class test {

    public static void main(String[] args) {
        BigDecimal totalMoney = new BigDecimal(71000);
        BigDecimal unpaidAmount = new BigDecimal(7000);
        System.out.println("unpaidAmount.compareTo(totalMoney) = " + unpaidAmount.compareTo(totalMoney));
    }
}
