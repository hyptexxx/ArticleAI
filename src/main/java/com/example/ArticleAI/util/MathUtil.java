package com.example.ArticleAI.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public class MathUtil {
    public double[] stableSoftmax(final double[] args) {
        final double[] log = Arrays.stream(args)
                .map(Math::log)
                .toArray();

        final double[] numenator = Arrays.stream(Arrays.stream(log)
                .map(operand -> operand - Arrays.stream(log)
                        .max()
                        .orElseThrow(IllegalArgumentException::new))
                .toArray()
        ).map(Math::exp)
                .toArray();

        return Arrays.stream(numenator)
                .map(operand -> operand / Arrays.stream(numenator).sum())
                .toArray();
    }
}
