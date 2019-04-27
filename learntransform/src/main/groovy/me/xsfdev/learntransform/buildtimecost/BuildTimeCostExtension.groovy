package me.xsfdev.learntransform.buildtimecost;

/**
 * Description:
 * <p>
 * Author xsf
 * Date 2019-04-27
 */
public class BuildTimeCostExtension {

    //task执行时间超过该值才会统计
    int threshold

    //是否按照task执行时长进行排序，true-表示从大到小进行排序，false-表示不排序
    boolean sorted

    void threshold(int threshold) {
        this.threshold = threshold
    }

    void sorted(boolean sorted) {
        this.sorted = sorted
    }

}
