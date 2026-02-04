<div align="center">

<img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
<img src="https://img.shields.io/badge/Spring%20Boot-2.7.6-green?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot"/>
<img src="https://img.shields.io/badge/MySQL-8.0+-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"/>
<img src="https://img.shields.io/badge/Redis-6.0+-DC382D?style=for-the-badge&logo=redis&logoColor=white" alt="Redis"/>
<img src="https://img.shields.io/badge/License-MIT-blue?style=for-the-badge" alt="License"/>

# 🚀 面试通刷题平台

### **高效刷题，助力技术面试**

[![Star History Chart](https://img.shields.io/github/stars/apprenticedyc/8gu-Trainer?style=social)](https://github.com/apprenticedyc/8gu-Trainer)
[![Forks](https://img.shields.io/github/forks/apprenticedyc/8gu-Trainer?style=social)](https://github.com/apprenticedyc/8gu-Trainer/network)
[![Issues](https://img.shields.io/github/issues/apprenticedyc/8gu-Trainer)](https://github.com/apprenticedyc/8gu-Trainer/issues)

[项目简介](#-项目简介) • [核心功能](#-核心功能) • [技术架构](#-技术架构) • [快速开始](#-快速开始)  • [API文档](#-API文档) • [项目亮点](#-项目亮点) • [贡献指南](#-贡献指南)

</div>

---

## ✨ 项目简介

**面试刷题平台** 是一个基于 Spring Boot 的技术面试题目管理系统，为用户提供高效题目检索和刷题记录服务。管理员可对题目资源进行分类和批量管理。


### 💎 项目亮点

| 特性 | 描述 |
|:---:|:---|
| 🎯 **智能搜索** | 基于 Elasticsearch 的高效题目搜索，支持全文检索 |
| ⚡ **批量操作** | 支持题目批量删除、批量导入/移出题库，提升操作效率 |
| 🛡️ **限流保护** | Sentinel 实现精细化流量控制，保护系统稳定性 |
| 🚀 **本地缓存** | 集成京东 Hotkey，自动缓存热点数据 |

### 🤔 **为什么做这个项目？**

<details>
<summary><b>项目价值与技术深度</b></summary>

**实战导向**
- 基于真实业务场景设计
- 涵盖企业级开发最佳实践

**中间件应用**
- Nacos 配置中心动态黑名单拦截
- Sentinel 多维度灵活流量控制
- Redis BitMap实现签到场景
- ES 搜索引擎提高全文搜索速度

**性能优化**
- 批处理任务拆分
- 异步任务并发执行
- ES 智能分词配置

</details>


---

## 📦 核心功能

### 🏗️ 业务架构

<div align="center">

```mermaid
graph TB
    subgraph "安全防护层"
        A[黑名单拦截]
        style A fill:#ffebee,stroke:#333,stroke-width:2px
    end

    subgraph "核心业务层"
        B[用户管理]
        C[题目管理]
        D[题库管理]
        style B fill:#f9f,stroke:#333,stroke-width:2px
        style C fill:#e6f3ff,stroke:#333,stroke-width:2px
        style D fill:#e6f3ff,stroke:#333,stroke-width:2px
    end

    subgraph "支撑服务层"
        E[流量控制]
        F[热点缓存]
        G[全文搜索]
        style E fill:#f0fff0,stroke:#333,stroke-width:2px
        style F fill:#f0fff0,stroke:#333,stroke-width:2px
        style G fill:#f0fff0,stroke:#333,stroke-width:2px
    end

    subgraph "数据存储层"
        H[(MySQL)]
        I[(Redis)]
        J[(ES)]
        style H fill:#fff2cc,stroke:#333,stroke-width:2px
        style I fill:#fff2cc,stroke:#333,stroke-width:2px
        style J fill:#fff2cc,stroke:#333,stroke-width:2px
    end

    %% 主要连接
    A --> B
    A --> C
    A --> D

    B --> C
    B --> D

    C --> E
    C --> G

    D --> F
    D --> E

    %% 数据连接
    B --> H
    C --> H
    D --> H

    E --> I
    F --> I
    G --> J
```

</div>

<details>
<summary><b>🚫 黑名单拦截模块</b></summary>

<div align="left">

| 功能 | 描述 |
|:---|:---|
| 🚫 **IP拦截** | 基于布隆过滤器的快速IP黑名单判断 |
| ⚡ **性能优化** | 每毫秒处理1000+请求的高效拦截 |
| 🔄 **实时更新** | Nacos配置变更，自动刷新黑名单 |

</div>

</details>

<details>
<summary><b>👤 用户管理模块</b></summary>

<div align="left">

| 功能            | 描述          |
|:--------------|:------------|
| 👤 **用户信息**   | 用户注册、个人信息管理 |
| 📊 **用户签到分析** | 记录用户年度刷题日期  |
| 🔧 **用户管理**   | 管理员用户管理功能   |


</div>

</details>

<details>
<summary><b>📝 题目管理模块</b></summary>

<div align="left">

| 功能           | 描述                  |
|:-------------|:--------------------|
| 📝 **CRUD**  | 创建、删除、更新、查询题目（仅管理员） |
| 📊 **题目展示**  | 分页获取题目列表    |
| 🔍 **智能搜索**  | 基于ES的高效全文检索         |
| 🗂️ **批量操作** | 支持题目批量删除            |
</div>

</details>

<details>
<summary><b>📚 题库管理模块</b></summary>

<div align="left">

| 功能           | 描述                  |
|:-------------|:--------------------|
| 📚 **CRUD**  | 创建、删除、更新、查询题库（仅管理员） |
| 📊 **题库展示**  | 分页获取题库列表            |
| 🎯 **题目关联**  | 题目批量添加/移出题库         |
| 🔍 **智能缓存**  | 基于Hotkey的题库热点数据缓存   |
| 🛡️ **限流保护** | Sentinel流量控制和熔断保护   |

</div>

</details>



---

### 📋 业务流程

#### 🚫 黑名单拦截流程

```mermaid
sequenceDiagram
    participant U as 用户
    participant WF as 过滤器
    participant BF as 布隆过滤器
    participant N as 配置中心
    participant R as 缓存

    U->>WF: HTTP请求
    WF->>BF: IP黑名单检查
    alt IP在黑名单中
        WF-->>U: 403 拦截访问
        note over WF: 记录日志
    else IP不在黑名单中
        WF->>R: 检查缓存
        alt 缓存未失效
            WF-->>U: 正常处理请求
        else 缓存失效
            WF->>N: 拉取最新配置
            N->>BF: 更新BloomFilter
            N->>R: 更新Redis缓存
            WF-->>U: 正常处理请求
        end
    end
```

**技术亮点**：
- 🔄 **实时更新**：Nacos配置变更自动刷新黑名单
- ⚡ **高性能**：BloomFilter毫秒级判断，每秒处理1000+请求
- 💾 **内存优化**：BitMap存储，占用空间小

---

#### 🔍 题目搜索流程

```mermaid
sequenceDiagram
    participant U as 用户
    participant C as 控制器
    participant S as 服务层
    participant ES as Elasticsearch

    U->>C: 搜索题目请求
    C->>S: 接收搜索关键词
    S->>S: 构建查询条件
    alt 关键词搜索
        S->>S: 构建全文检索条件
        S->>S: 添加多字段匹配（标题、内容、答案）
    else 过滤条件
        S->>S: 添加过滤条件
        S->>S: 按标签、用户、题库等过滤
    end
    S->>ES: 执行ES搜索
    ES-->>S: 返回搜索结果
    S->>S: 处理搜索结果
    S->>S: 封装分页信息
    S-->>C: 返回结果
    C-->>U: 显示搜索结果
```

**技术亮点**：
- 🔍 **全文检索**：支持多字段全文搜索（标题、内容、答案）
- 📊 **精准过滤**：支持按标签、用户、题库等条件过滤
- 🔄 **数据同步**：通过定时任务增量同步MySQL → ES
---

#### 📚 题库管理流程

```mermaid
sequenceDiagram
    participant U as 用户
    participant SC as Sentinel流量控制
    participant C as 控制器
    participant S as 服务层
    participant H as Hotkey缓存
    participant DB as 数据库

    U->>C: 请求分页获取题库列表
    C->>SC: Sentinel流量检查
    alt 触发限流(单IP>60次/分钟)
        SC-->>U: 429 请求太频繁
        note over SC: 返回系统繁忙提示
    else 触发熔断(异常率>10%或慢查询>20%)
        SC-->>U: 503 服务暂时不可用
        note over SC: 降级处理，返回空数据
    else 正常流量
        SC->>C: 放行请求
        C->>H: 检查Hotkey缓存
        alt 热点命中
            H-->>C: 返回缓存数据
            C-->>U: 快速响应(<10ms)
        else 非热点
            C->>S: 调用服务层
            S->>DB: 查询数据库
            DB-->>S: 返回数据
            S->>H: 更新Hotkey热点探测
            S-->>C: 返回结果
            C-->>U: 返回题库列表
        end
    end
```

**技术亮点**：
- 🛡️ **智能限流**：基于Sentinel的流量控制
  - 单IP每分钟最多60次请求
  - 慢查询比例>20%时熔断60秒
  - 异常率>10%时熔断60秒
- 🔥 **热点探测**：自动识别热门题库并存入本地缓存
- 📊 **降级保护**：限流时返回默认数据，保证可用性
- 🎯 **精准防护**：基于IP地址的热点参数精细化限流

---

#### 📝 题目批量操作流程

```mermaid
sequenceDiagram
    participant U as 用户
    participant C as 控制器
    participant S as 服务层
    participant TS as 事务服务
    participant ES as 搜索引擎
    participant DB as 数据库

    U->>C: 批量删除请求
    C->>S: 验证数据权限
    S->>S: 拆分数据为小批次
    alt 批次处理
        loop 分批处理
            S->>TS: 开启事务
            TS->>DB: 批量删除题目（小批次）
            loop 单批处理
                TS->>DB: 删除单条记录
                TS->>ES: 同步删除ES数据
                TS->>DB: 记录操作日志
            end
            TS->>TS: 提交事务
        end
    else 处理失败
        S->>S: 回滚当前批次
    end
    S->>C: 返回处理结果
    C-->>U: 显示处理结果
```

**技术亮点**：
- 🔄 **分批操作**：拆分为多个小批次，避免长事务风险
- ⚡ **批量优化**：批量操作减少数据库交互次数
- 📊 **操作日志**：记录所有批量操作轨迹
- 🚫 **异常处理**：失败时单批次数据自动回滚，保证数据完整性

---

## 🛠️ 技术架构

### 后端框架

<div align="center">

<img src="https://img.shields.io/badge/Spring%20Boot-2.7.6-6DB33F?style=flat-square&logo=springboot&logoColor=white" alt="Spring Boot"/>
<img src="https://img.shields.io/badge/Java-17-ed8b00?style=flat-square&logo=openjdk&logoColor=white" alt="Java"/>
<img src="https://img.shields.io/badge/MyBatis%20Plus-3.5.2-CC2927?style=flat-square" alt="MyBatis Plus"/>

</div>

### 微服务组件

<div align="center">

<img src="https://img.shields.io/badge/Nacos-Config-FF6A00?style=flat-square&logo=nacos&logoColor=white" alt="Nacos"/>
<img src="https://img.shields.io/badge/Sentinel-Flow-Control-25A8FF?style=flat-square&logo=sentinel&logoColor=white" alt="Sentinel"/>
<img src="https://img.shields.io/badge/Redisson-Redis-DC382D?style=flat-square" alt="Redisson"/>
<img src="https://img.shields.io/badge/Hotkey-Local%20Cache-FF6B6B?style=flat-square" alt="Hotkey"/>

</div>

### 数据存储

<div align="center">

<img src="https://img.shields.io/badge/MySQL-8.0+-4479A1?style=flat-square&logo=mysql&logoColor=white" alt="MySQL"/>
<img src="https://img.shields.io/badge/Redis-6.0+-DC382D?style=flat-square&logo=redis&logoColor=white" alt="Redis"/>
<img src="https://img.shields.io/badge/Elasticsearch-8.0+-005571?style=flat-square&logo=elasticsearch&logoColor=white" alt="Elasticsearch"/>

</div>

### 工具

<div align="center">

<img src="https://img.shields.io/badge/Knife4j-4.4.0-00E676?style=flat-square" alt="Knife4j"/>
<img src="https://img.shields.io/badge/Hutool-5.8.38-ff6b6b?style=flat-square" alt="Hutool"/>
<img src="https://img.shields.io/badge/Lombok-1.18.30-2962FF?style=flat-square" alt="Lombok"/>

</div>

---

## 🚀 快速开始

### 环境要求

| 组件 | 版本要求     |
|:---|:---------|
| JDK | **8+**   |
| Maven | **3.6+** |
| MySQL | **8.0+** |
| Redis | **6.0+** |
| Elasticsearch | **8.0+** |

---

### 安装步骤

#### 1️⃣ 克隆仓库

```bash
git clone https://github.com/mianshi/mianshi-backend.git
cd mianshi-backend
```

#### 2️⃣ 初始化数据库

```bash
# 创建数据库
mysql -u root -p -e "CREATE DATABASE `mianshi_platform` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# 导入表结构
mysql -u root -p mianshi_platform < sql/init.sql
```

#### 3️⃣ 配置应用

编辑 `src/main/resources/application.yaml`：

```yaml
server:
  port: 8123

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mianshi_platform
    username: your_username
    password: your_password
  data:
    redis:
      host: localhost
      port: 6379
      password: your_redis_password
      database: 0

# Nacos配置中心
nacos:
  server-addr: localhost:8848
  namespace: dev
  username: nacos
  password: nacos

# Elasticsearch配置
elasticsearch:
  host: localhost
  port: 9200
```

#### 4️⃣ 配置限流规则

编辑 `src/main/resources/sentinel.yaml` 或通过 Sentinel 控制台配置。

#### 5️⃣ 构建并启动

```bash
# 构建
mvn clean install -DskipTests

# 启动
mvn spring-boot:run
```

---

## 🔌 API 文档

### 核心接口

<details>
<summary><b>👤 用户相关</b></summary>

| 接口 | 方法 | 描述 |
|:---|:---:|:---|
| `/api/user/register` | POST | 用户注册 |
| `/api/user/login` | POST | 用户登录 |
| `/api/user/logout` | POST | 用户登出 |
| `/api/user/get/login` | GET | 获取当前登录用户 |

</details>

<details>
<summary><b>📝 题目相关</b></summary>

| 接口 | 方法 | 描述 |
|:---|:---:|:---|
| `/api/question/add` | POST | 创建题目 |
| `/api/question/delete` | POST | 删除题目 |
| `/api/question/update` | POST | 更新题目 |
| `/api/question/get/vo` | GET | 获取题目详情 |
| `/api/question/list/page/vo` | POST | 分页获取题目列表 |
| `/api/question/list/page/vo/sentinel` | POST | 分页获取题目列表（限流版） |
| `/api/question/my/list/page/vo` | POST | 获取我的题目列表 |
| `/api/question/search/page/vo` | POST | 搜索题目 |
| `/api/question/delete/batch` | POST | 批量删除题目 |

</details>

<details>
<summary><b>📚 题库相关</b></summary>

| 接口 | 方法 | 描述 |
|:---|:---:|:---|
| `/api/questionBank/add` | POST | 创建题库 |
| `/api/questionBank/update` | POST | 更新题库 |
| `/api/questionBank/delete` | POST | 删除题库 |
| `/api/questionBank/get/vo` | GET | 获取题库详情 |
| `/api/questionBank/list/page/vo` | POST | 分页获取题库列表 |
| `/api/questionBankQuestion/add` | POST | 添加题目到题库 |
| `/api/questionBankQuestion/batch/add` | POST | 批量添加题目到题库 |
| `/api/questionBankQuestion/delete` | POST | 从题库移除题目 |

</details>

> 📖 **完整文档**：启动项目后访问 [Knife4j 在线文档](http://localhost:8123/doc.html)

---



## 🤝 贡献指南

### 开发环境

1. Fork 本仓库
2. 创建特性分支：`git checkout -b feature/your-feature`
3. 提交更改：`git commit -m 'feat: add new feature'`
4. 推送分支：`git push origin feature/your-feature`
5. 创建 Pull Request

### 代码规范

- 遵循 [阿里巴巴 Java 开发手册](https://github.com/alibaba/p3c)
- 使用 Lombok 减少样板代码
- 必须添加单元测试
- 编写规范的文档注释

### Commit 规范

遵循 [Conventional Commits](https://www.conventionalcommits.org/) 规范：

- `feat:` 新功能
- `fix:` 修复问题
- `docs:` 文档更新
- `style:` 代码格式
- `refactor:` 代码重构
- `test:` 测试相关
- `chore:` 构建/工具相关

---

## 📄 许可证

本项目采用 [MIT](LICENSE) 许可证开源。

---

<div align="center">

## ⭐ Star History

[![Star History Chart](https://api.star-history.com/svg?repos=apprenticedyc/8gu-Trainer&type=Date)](https://star-history.com/#apprenticedyc/8gu-Trainer&Date)

---

**如果这个项目对你有帮助，请给一个 Star ⭐**

Made with ❤️ by [ApprenticeDyc](https://github.com/apprenticedyc)

</div>
