package com.webapp.bankingportal.mapper;

import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

import com.webapp.bankingportal.entity.User;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public void updateUser(User source, User target) {
        if (source == null || target == null) {
            return;
        }
        String[] nullProps = getNullPropertyNames(source);
        BeanUtils.copyProperties(source, target, nullProps);
    }

    private String[] getNullPropertyNames(User source) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass(), Object.class);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            List<String> nullNames = new ArrayList<>();
            for (PropertyDescriptor pd : pds) {
                try {
                    Method readMethod = pd.getReadMethod();
                    if (readMethod != null) {
                        Object value = readMethod.invoke(source);
                        if (value == null) {
                            nullNames.add(pd.getName());
                        }
                    }
                } catch (Exception e) {
                    // ignore and continue with other properties
                }
            }
            return nullNames.toArray(new String[0]);
        } catch (Exception e) {
            return new String[0];
        }
    }
}
