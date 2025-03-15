package com.jc.asyncstatement.OutboundController;

public interface GeneralOutboundController<R, S> {
    public S doOut(R reqVo);
}
