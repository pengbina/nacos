/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos.config.server.model;

import com.alibaba.nacos.config.server.constant.Constants;
import com.alibaba.nacos.config.server.utils.SimpleReadWriteLock;
import com.alibaba.nacos.core.utils.StringPool;

import java.util.List;
import java.util.Map;

/**
 * Cache item.
 *
 * @author Nacos
 *
 * 从整体上Nacos服务端的配置存储分为三层：
 * 内存：Nacos每个节点都在内存里缓存了配置，但是只包含配置的md5（缓存配置文件太多了），所以内存级别的配置只能用于比较配置是否发生了变更，
 * 只用于客户端长轮询配置等场景。
 * 文件系统：文件系统配置来源于数据库写入的配置。如果是集群启动或mysql单机启动，服务端会以本地文件系统的配置响应客户端查询。
 * 数据库：所有写数据都会先写入数据库。只有当以derby数据源（-DembeddedStorage=true）单机（-Dnacos.standalone=true）启动时，
 * 客户端的查询配置请求会实时查询derby数据库。
 *
 * 对于写请求，Nacos会将数据先更新到数据库，之后异步写入所有节点的文件系统并更新内存。
 * CacheItem在Nacos服务端对应一个配置文件，缓存了配置的md5，持有一把读写锁控制访问冲突。
 */
public class CacheItem {
    
    public CacheItem(String groupKey) {
        this.groupKey = StringPool.get(groupKey);
    }
    
    public String getMd5() {
        return md5;
    }
    
    public void setMd5(String md5) {
        this.md5 = md5;
    }
    
    public long getLastModifiedTs() {
        return lastModifiedTs;
    }
    
    public void setLastModifiedTs(long lastModifiedTs) {
        this.lastModifiedTs = lastModifiedTs;
    }
    
    public boolean isBeta() {
        return isBeta;
    }
    
    public void setBeta(boolean isBeta) {
        this.isBeta = isBeta;
    }
    
    public String getMd54Beta() {
        return md54Beta;
    }
    
    public void setMd54Beta(String md54Beta) {
        this.md54Beta = md54Beta;
    }
    
    public List<String> getIps4Beta() {
        return ips4Beta;
    }
    
    public void setIps4Beta(List<String> ips4Beta) {
        this.ips4Beta = ips4Beta;
    }
    
    public long getLastModifiedTs4Beta() {
        return lastModifiedTs4Beta;
    }
    
    public void setLastModifiedTs4Beta(long lastModifiedTs4Beta) {
        this.lastModifiedTs4Beta = lastModifiedTs4Beta;
    }
    
    public SimpleReadWriteLock getRwLock() {
        return rwLock;
    }
    
    public void setRwLock(SimpleReadWriteLock rwLock) {
        this.rwLock = rwLock;
    }
    
    public String getGroupKey() {
        return groupKey;
    }
    
    public Map<String, String> getTagMd5() {
        return tagMd5;
    }
    
    public Map<String, Long> getTagLastModifiedTs() {
        return tagLastModifiedTs;
    }
    
    public void setTagMd5(Map<String, String> tagMd5) {
        this.tagMd5 = tagMd5;
    }
    
    public void setTagLastModifiedTs(Map<String, Long> tagLastModifiedTs) {
        this.tagLastModifiedTs = tagLastModifiedTs;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    // groupKey = namespace + group + dataId
    final String groupKey;
    // 配置md5
    public volatile String md5 = Constants.NULL;
    // 更新时间
    public volatile long lastModifiedTs;
    
    /**
     * Use for beta.
     */
    public volatile boolean isBeta = false;
    
    public volatile String md54Beta = Constants.NULL;
    
    public volatile List<String> ips4Beta;
    
    public volatile long lastModifiedTs4Beta;
    
    public volatile Map<String, String> tagMd5;
    
    public volatile Map<String, Long> tagLastModifiedTs;
    // nacos自己实现的简版读写锁
    public SimpleReadWriteLock rwLock = new SimpleReadWriteLock();
    // 配置文件类型：text/properties/yaml
    public String type;
    
    public String encryptedDataKey = Constants.NULL;
    
    public String encryptedDataKeyBeta = Constants.NULL;
    
    public String getEncryptedDataKey() {
        return encryptedDataKey;
    }
    
    public void setEncryptedDataKey(String encryptedDataKey) {
        this.encryptedDataKey = encryptedDataKey;
    }
    
    public String getEncryptedDataKeyBeta() {
        return encryptedDataKeyBeta;
    }
    
    public void setEncryptedDataKeyBeta(String encryptedDataKeyBeta) {
        this.encryptedDataKeyBeta = encryptedDataKeyBeta;
    }
}
