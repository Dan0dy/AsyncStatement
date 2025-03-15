package com.jc.asyncstatement.OutboundController;

import com.jc.asyncstatement.outboundVo.TemplateEngineReqVo;
import com.jc.asyncstatement.outboundVo.TemplateEngineRespVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TemplateEngineController implements GeneralOutboundController<TemplateEngineReqVo, TemplateEngineRespVo> {

    @PostMapping("/pdf")
    public TemplateEngineRespVo doOut(@RequestBody TemplateEngineReqVo reqVo) {
        return new TemplateEngineRespVo("JVBERi0xLjUKJYCBgoMKMSAwIG9iago8PC9GaWx0ZXIvRmxhdGVEZWNvZGUvRmlyc3QgMTQxL04gMjAvTGVuZ3==");
    }
}
