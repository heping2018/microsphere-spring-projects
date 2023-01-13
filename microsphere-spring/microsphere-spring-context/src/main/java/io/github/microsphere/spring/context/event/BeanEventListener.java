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
import org.springframework.beans.factory.config.BeanDefinition;

import java.util.EventListener;

/**
 * Bean {@link EventListener}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
public interface BeanEventListener extends EventListener {

    void onBeanDefinitionReady(String beanName, BeanDefinition beanDefinition);

    void beforeBeanInstantiate(String beanName, Class<?> beanClass);

    void beanInstantiated(String beanName, Object bean);

    void afterBeanInstantiated(String beanName, Object bean);

    void onPropertyValuesReady(String beanName, Object bean, PropertyValues pvs);

    void beforeBeanInitialize(String beanName, Object bean);

    void afterBeanInitialized(String beanName, Object bean);

    void onBeanReady(String beanName, Object bean);

    void beforeBeanDestroy(String beanName, Object bean);

    void afterBeanDestroy(String beanName, Object bean);
}
