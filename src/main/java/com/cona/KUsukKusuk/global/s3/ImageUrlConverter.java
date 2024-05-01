package com.cona.KUsukKusuk.global.s3;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class ImageUrlConverter {

    private static String s3BaseUrl;
    private static String cloudFrontBaseUrl;

    @Value("${cloud.aws.s3.bucketName}")
    public void setS3BaseUrl(String bucket) {
        s3BaseUrl = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/";
    }

    @Value("${cloud.aws.cloudfront.domainName}")
    public void setCloudFrontBaseUrl(String cloudfront) {
        cloudFrontBaseUrl = "https://" + cloudfront + "/";
    }

    public static String convertToCloudFrontUrl(String imageUrl) {
        return imageUrl.replace(s3BaseUrl, cloudFrontBaseUrl);
    }

    public static List<String> convertToCloudFrontUrls(List<String> imageUrls) {
        return imageUrls.stream()
                .map(ImageUrlConverter::convertToCloudFrontUrl)
                .collect(Collectors.toList());
    }
}
