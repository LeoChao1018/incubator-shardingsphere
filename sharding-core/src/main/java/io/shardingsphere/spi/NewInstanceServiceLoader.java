/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.spi;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * SPI service loader for new instance for every call.
 *
 * @author zhangliang
 * @author zhaojun
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NewInstanceServiceLoader {
    
    private static final Map<Class, Collection> SERVICE_MAP = new HashMap<>();
    
    /**
     * Load SPI service, hold the class in service map for new instance.
     * 
     * @param service service type
     * @param <T> type of service
     * @return new service class loader
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> load(final Class<T> service) {
        Collection<T> result = new LinkedList<>();
        for (T each : ServiceLoader.load(service)) {
            Collection<Class<T>> serviceClasses = SERVICE_MAP.get(service);
            if (null == serviceClasses) {
                serviceClasses = new LinkedList<>();
            }
            serviceClasses.add((Class<T>) each.getClass());
            SERVICE_MAP.put(service, serviceClasses);
            result.add(each);
        }
        return result;
    }
}
