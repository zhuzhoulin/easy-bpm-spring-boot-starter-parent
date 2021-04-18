package com.pig.easy.bpm.web.controller.common;

import com.google.code.kaptcha.Producer;
import com.pig.easy.bpm.auth.core.controller.BaseController;
import com.pig.easy.bpm.auth.core.redis.RedisCache;
import com.pig.easy.bpm.common.annotation.Login;
import com.pig.easy.bpm.common.constant.Constants;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.id.IdUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 *
 * @author pig
 */
@RestController
@RequestMapping("/common/captcha")
@Login(false)
public class CaptchaController extends BaseController {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;

    /**
     * 验证码类型
     */
    @Value("${easy.bpm.config.captchaType}")
    private String captchaType;

    /**
     * 生成验证码
     */
    @ApiOperation(value = "新增", notes = "新增", produces = "application/json")
    @GetMapping("/captchaImage")
    public JsonResult getCode(HttpServletResponse response) throws IOException {
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.error(EntityError.SYSTEM_ERROR);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("uuid", uuid);
        result.put("img", new BASE64Encoder().encode(os.toByteArray()));
        return JsonResult.success(result);
    }
}
