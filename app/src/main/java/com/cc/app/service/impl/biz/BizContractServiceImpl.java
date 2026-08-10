package com.cc.app.service.impl.biz;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.biz.BizContract;
import com.cc.core.mapper.biz.BizContractMapper;
import com.cc.core.service.biz.BizContractService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalDate;
import com.cc.core.service.biz.BizContractChangeLogService;
import com.cc.core.entity.biz.BizContractChangeLog;

/**
 * BizContract 服务实现
 */
@Service
@RequiredArgsConstructor
public class BizContractServiceImpl extends ServiceImpl<BizContractMapper, BizContract> implements BizContractService {
    private final BizContractChangeLogService changeLogService;


    // ==== Business Logic Methods ====

    @Override
    public void recordChange(Long contractId, String changeType, String beforeValue, String afterValue,
            String reason, String changedBy) {
        BizContractChangeLog log = new BizContractChangeLog();
        log.setContractId(contractId);
        log.setChangeType(changeType);
        log.setBeforeValue(beforeValue);
        log.setAfterValue(afterValue);
        log.setChangeReason(reason);
        log.setChangedBy(changedBy);
        log.setChangedTime(java.time.LocalDateTime.now().toString());
        changeLogService.save(log);
    }

    @Override
    public java.util.List<java.util.Map<String, Object>> executeQuery(Long id) {
        BizContract contract = getById(id);
        java.util.Map<String, Object> info = new java.util.HashMap<>();
        info.put("contractNo", contract.getContractNo());
        info.put("contractName", contract.getContractName());
        info.put("contractAmount", contract.getContractAmount());
        info.put("signedAmount", contract.getSignedAmount());
        info.put("status", contract.getStatus());
        info.put("startDate", contract.getStartDate());
        info.put("endDate", contract.getEndDate());
        return java.util.List.of(info);
    }

    @Override
    public BizContract getByCode(String code) {
        return lambdaQuery().eq(BizContract::getContractNo, code).one();
    }

}
