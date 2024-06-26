package com.ssms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ssms.dto.actiontype.ActionTypeBean;
import org.springframework.stereotype.Service;

import com.ssms.constants.ApplicationConstants;
import com.ssms.entity.ActionType;
import com.ssms.repository.ActionTypeRepository;
import com.ssms.service.ActionTypeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ActionTypeServiceImpl implements ActionTypeService {

    private final ActionTypeRepository actionTypeRepo;

    @Override
    public Map<String, Object> getAllActiveActionTypes() {
        List<ActionType> actionTypesList = null;
        Map<String, Object> responseMap = new HashMap<>();
        actionTypesList = actionTypeRepo.findAllByStatus(ApplicationConstants.CustomerConstants.ACTION_TYPE_ACTIVE);
        responseMap.put("ActionTypeList", null);
        responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
        if (actionTypesList != null && !actionTypesList.isEmpty()) {
            List<ActionTypeBean> actionTypeBeanList = actionTypesList.stream()
                    .map(this::mapToActionTypeBean)
                    .collect(Collectors.toList());
            responseMap.put("ActionTypeList", actionTypeBeanList);
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> addActionType(ActionType actionTypeBean) {
        List<ActionType> actionTypes = null;
        actionTypes = actionTypeRepo.findAll();
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);

        Optional<ActionType> queryResult = actionTypes.stream().filter(
                        actionType -> actionType.getActionType().trim().equalsIgnoreCase(actionTypeBean.getActionType().trim()))
                .findFirst();

        if (queryResult.isPresent()) {
            responseMap.put("message", "This action type already exists!");
            responseMap.put("errorCode", "WSEC001");
        } else {
            ActionType actionType = new ActionType();
            actionType.setActionType(actionTypeBean.getActionType().trim());
            actionType.setStatus(ApplicationConstants.CustomerConstants.ACTION_TYPE_ACTIVE);
            actionTypeRepo.save(actionType);
            responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
            responseMap.put("message", "Action type added.");
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> updateActionType(ActionType actionTypeBean) {
        ActionType theActionType = null;

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
        responseMap.put("message", "Failed to update action type!");

        if (actionTypeBean.getActionTypeId() != null && actionTypeBean.getActionTypeId() > 0) {
            theActionType = actionTypeRepo.findById(actionTypeBean.getActionTypeId()).get();

            if (theActionType != null && theActionType.getActionTypeId() > 0) {
                theActionType.setStatus(actionTypeBean.getStatus());
                actionTypeRepo.save(theActionType);
                responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
                responseMap.put("message", "Action type updated succesfully!");
            }
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> getActionTypeById(Long actionTypeId) {
        ActionType actionType = null;
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
        responseMap.put("message", "Failed to get action type!");

        if (actionTypeId != null & actionTypeId > 0) {
            actionType = actionTypeRepo.findById(actionTypeId).orElse(null);
            if (actionType != null && actionType.getActionTypeId() > 0) {
                ActionTypeBean actionTypeBean = mapToActionTypeBean(actionType);
                responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
                responseMap.put("message", "");
                responseMap.put("ActionType", actionTypeBean);
            } else {
                responseMap.put("message", "No action type of id " + actionTypeId + " found!");
            }
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> deleteActionType(Long actionTypeId) {
        ActionType actionType = null;
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
        responseMap.put("message", "Failed to delete action type!");

        if (actionTypeId != null & actionTypeId > 0) {
            actionType = actionTypeRepo.findById(actionTypeId).orElse(null);
            if (actionType != null && actionType.getActionTypeId() > 0) {
                actionTypeRepo.delete(actionType);
                responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
                responseMap.put("message", "Action type deleted successfully!");
            }
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> getAllActionTypes() {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("ActionTypeList", null);
        responseMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
        List<ActionTypeBean> actionTypeBeanList =
                actionTypeRepo.findAll()
                        .stream()
                        .map(this::mapToActionTypeBean)
                        .collect(Collectors.toList());
        responseMap.put("ActionTypeList", actionTypeBeanList);
        return responseMap;
    }

    private ActionTypeBean mapToActionTypeBean(ActionType actionType) {
        return ActionTypeBean.builder()
                .actionTypeId(actionType.getActionTypeId())
                .actionType(actionType.getActionType())
                .build();
    }

}