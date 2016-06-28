package com.tomtop.image.services.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.amazonaws.services.cloudfront.AmazonCloudFrontAsyncClient;
import com.amazonaws.services.cloudfront.model.CreateInvalidationRequest;
import com.amazonaws.services.cloudfront.model.CreateInvalidationResult;
import com.amazonaws.services.cloudfront.model.Invalidation;
import com.amazonaws.services.cloudfront.model.InvalidationBatch;
import com.amazonaws.services.cloudfront.model.Paths;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.tomtop.image.models.bo.AmazonStatusEnum;

/**
 * 亚马逊服务类
 * 
 * @author lijun
 *
 */
@Service
public class AmazonawsService {

	private static Logger logger = LoggerFactory
			.getLogger(AmazonawsService.class);

	@Value("${amazon.distribution}")
	private String distribution;

	@Value("${amazon.bucketName}")
	private String bucketName;

	@Autowired
	AmazonCloudFrontAsyncClient client;

	@Autowired
	AmazonS3Client s3Client;

	/**
	 * 刷新资源
	 * 
	 * @param paths
	 * @return
	 */
	public AmazonStatusEnum invalidation(Paths paths) {
		if (paths == null || paths.getItems().size() == 0) {
			throw new NullPointerException("paths is null");
		}
		try {

			InvalidationBatch invalidationBatch = new InvalidationBatch();
			invalidationBatch.setPaths(paths);
			invalidationBatch.setCallerReference(UUID.randomUUID().toString());

			CreateInvalidationRequest request = new CreateInvalidationRequest();
			request.setDistributionId(distribution);
			request.setInvalidationBatch(invalidationBatch);

			CreateInvalidationResult re = client.createInvalidation(request);
			Invalidation fb = re.getInvalidation();
			String state = fb.getStatus();

			AmazonStatusEnum result = AmazonStatusEnum.valueOf(state);

			return result;
		} catch (Exception e) {
			logger.error("invalidation error", e);
			return AmazonStatusEnum.error;
		}
	}

	/**
	 * @author lijun
	 * @param bytes
	 * @param contentType
	 * @param relativePath
	 */
	public void upload(byte[] bytes, String contentType, String relativePath) {
		if (bytes == null || bytes.length == 0) {
			return;
		}
		Assert.hasLength(contentType);
		Assert.hasLength(relativePath);

		ObjectMetadata md = new ObjectMetadata();
		md.setContentType(contentType);
		md.setContentLength(bytes.length);

		InputStream input = new ByteArrayInputStream(bytes);
		PutObjectRequest request = new PutObjectRequest(bucketName,
				relativePath, input, md);
		PutObjectResult result = s3Client.putObject(request);
	}
}
