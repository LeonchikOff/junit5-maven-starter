package org.example.junit.extension;

import org.example.junit.dao.UserDao;
import org.example.junit.service.UserService;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class UserServiceParamResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == UserService.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        ExtensionContext.Store userServiceStore = extensionContext.getStore(ExtensionContext.Namespace.create(UserService.class));
        return userServiceStore.getOrComputeIfAbsent(UserService.class, userServiceClass -> new UserService(new UserDao()));
    }
}
