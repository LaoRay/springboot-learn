package com.zhengtoon.framework.cloud.configuration;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.zhengtoon.framework.cloud.CloudStorageConfigure;
import com.zhengtoon.framework.cloud.service.impl.FastDFSStrorageImpl;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;


/**
 * fastdfs-client 加载类
 * @author LuCheng.Qi
 * @since 2018-08-07
 * Company:北京思源政务通有限公司
 */
@Configuration
@Import(FdfsClientConfig.class)
@ConditionalOnProperty(name = "scloud.type", havingValue = "fastdfs")
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class FdfsConfigure {
}
