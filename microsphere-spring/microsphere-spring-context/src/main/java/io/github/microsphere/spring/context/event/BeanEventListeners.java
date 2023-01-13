/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.microsphere.spring.context.event;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

/**
 * The composite {@link BeanEventListener}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
class BeanEventListeners {

    private static final String BEAN_NAME = "beanEventListeners";

    private final List<BeanEventListener> listeners;

    public BeanEventListeners(ConfigurableListableBeanFactory beanFactory) {
        this.listeners = new ArrayList<>(beanFactory.getBeansOfType(BeanEventListener.class).values());
        AnnotationAwareOrderComparator.sort(listeners);
    }

    public void beanDefinitionReady(String beanName, BeanDefinition beanDefinition) {
        iterate(listener -> listener.beanDefinitionReady(beanName, beanDefinition));
    }

    public void beforeInstantiate(String beanName, Class<?> beanClass) {
        iterate(listener -> listener.beforeInstantiate(beanName, beanClass));
    }

    public void instantiated(String beanName, Object bean) {
        iterate(listener -> listener.instantiated(beanName, bean));
    }

    public void afterInstantiated(String beanName, Object bean) {
        iterate(listener -> listener.afterInstantiated(beanName, bean));
    }

    public void propertyValuesReady(String beanName, Object bean, PropertyValues pvs) {
        iterate(listener -> listener.propertyValuesReady(beanName, bean, pvs));
    }

    public void beforeInitialize(String beanName, Object bean) {
        iterate(listener -> listener.beforeInitialize(beanName, bean));
    }

    public void afterInitialize(String beanName, Object bean) {
        iterate(listener -> listener.afterInitialized(beanName, bean));
    }

    public void beforeDestroy(String beanName, Object bean) {
        iterate(listener -> listener.beforeDestroy(beanName, bean));
    }

    private void iterate(Consumer<BeanEventListener> listenerConsumer) {
        listeners.forEach(listenerConsumer);
    }

    public void registerBean(BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder beanDefinitionBuilder = rootBeanDefinition(BeanEventListeners.class, () -> this);
        beanDefinitionBuilder.setPrimary(true);
        registry.registerBeanDefinition(BEAN_NAME, beanDefinitionBuilder.getBeanDefinition());
    }

    public static BeanEventListeners getBean(BeanFactory beanFactory) {
        return beanFactory.getBean(BEAN_NAME, BeanEventListeners.class);
    }
}
