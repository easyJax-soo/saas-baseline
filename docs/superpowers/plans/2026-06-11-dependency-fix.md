# Dependency Fix Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Fix Maven dependency groupId mismatch causing IDEA red errors across all service modules.

**Architecture:** The services declare dependencies with `com.baseline.library` groupId, but common-library modules are published with `com.baseline` groupId. Also fixing typos and removing non-existent service references.

**Tech Stack:** Maven, Spring Boot, Spring Cloud

---

## Root Cause

Services use `com.baseline.library:cloud-common-X` but common-library modules declare themselves as `com.baseline:cloud-common-X`. The desensitization script incorrectly kept `library` in the groupId.

## Issues Found

1. **groupId mismatch** - all services use `com.baseline.library` instead of `com.baseline`
2. **auth-service pom.xml:107** - typo `cloud-common-common-biz` should be `cloud-common-biz`
3. **frame-service pom.xml:94** - typo `cloud-common-common-biz` should be `cloud-common-biz`
4. **frame-service single profile** - references non-existent services (manage-service, agriculture-service, etc.)

---

## Task 1: Fix auth-service dependencies

**Files:**
- Modify: `D:\project\baseline-project\baseline-backend\module-service\auth-service\pom.xml`

- [ ] **Step 1: Fix groupId and typo**

Replace all `com.baseline.library` with `com.baseline` and fix `cloud-common-common-biz` → `cloud-common-biz`:

```xml
<!-- Before -->
<groupId>com.baseline.library</groupId>
<artifactId>cloud-common-common-biz</artifactId>

<!-- After -->
<groupId>com.baseline</groupId>
<artifactId>cloud-common-biz</artifactId>
```

Run this sed command:
```bash
sed -i 's/com\.baseline\.library/com.baseline/g' D:/project/baseline-project/baseline-backend/module-service/auth-service/pom.xml
sed -i 's/cloud-common-common-biz/cloud-common-biz/g' D:/project/baseline-project/baseline-backend/module-service/auth-service/pom.xml
```

- [ ] **Step 2: Verify auth-service pom.xml**

Run: `grep -c "com.baseline.library" D:/project/baseline-project/baseline-backend/module-service/auth-service/pom.xml`
Expected: 0

Run: `grep -c "cloud-common-common-biz" D:/project/baseline-project/baseline-backend/module-service/auth-service/pom.xml`
Expected: 0

---

## Task 2: Fix system-service dependencies

**Files:**
- Modify: `D:\project\baseline-project\baseline-backend\module-service\system-service\pom.xml`

- [ ] **Step 1: Fix groupId**

```bash
sed -i 's/com\.baseline\.library/com.baseline/g' D:/project/baseline-project/baseline-backend/module-service/system-service/pom.xml
```

- [ ] **Step 2: Verify system-service pom.xml**

Run: `grep -c "com.baseline.library" D:/project/baseline-project/baseline-backend/module-service/system-service/pom.xml`
Expected: 0

---

## Task 3: Fix gateway-service dependencies

**Files:**
- Modify: `D:\project\baseline-project\baseline-backend\module-service\gateway-service\pom.xml`

- [ ] **Step 1: Fix groupId**

```bash
sed -i 's/com\.baseline\.library/com.baseline/g' D:/project/baseline-project/baseline-backend/module-service/gateway-service/pom.xml
```

- [ ] **Step 2: Verify gateway-service pom.xml**

Run: `grep -c "com.baseline.library" D:/project/baseline-project/baseline-backend/module-service/gateway-service/pom.xml`
Expected: 0

---

## Task 4: Fix frame-service dependencies

**Files:**
- Modify: `D:\project\baseline-project\baseline-backend\module-service\frame-service\pom.xml`

- [ ] **Step 1: Fix groupId and typo**

```bash
sed -i 's/com\.baseline\.library/com.baseline/g' D:/project/baseline-project/baseline-backend/module-service/frame-service/pom.xml
sed -i 's/cloud-common-common-biz/cloud-common-biz/g' D:/project/baseline-project/baseline-backend/module-service/frame-service/pom.xml
```

- [ ] **Step 2: Remove non-existent service dependencies**

The single profile references these non-existent services that need to be removed:
- manage-service
- agriculture-service
- cloud-common-device
- mcp-service
- points-service
- political-service
- village-service

Remove the entire `<dependencies>` block inside the `<profile><id>single</id>` section that contains these references, keeping only the core common-library dependencies.

- [ ] **Step 3: Verify frame-service pom.xml**

Run: `grep -c "com.baseline.library" D:/project/baseline-project/baseline-backend/module-service/frame-service/pom.xml`
Expected: 0

Run: `grep -c "cloud-common-common-biz" D:/project/baseline-project/baseline-backend/module-service/frame-service/pom.xml`
Expected: 0

Run: `grep -E "(manage-service|agriculture-service|cloud-common-device|mcp-service|points-service|political-service|village-service)" D:/project/baseline-project/baseline-backend/module-service/frame-service/pom.xml`
Expected: no matches

---

## Task 5: Verify all services

- [ ] **Step 1: Check all service pom.xml files for remaining issues**

Run:
```bash
grep -r "com.baseline.library" D:/project/baseline-project/baseline-backend/module-service/*/pom.xml
grep -r "cloud-common-common-biz" D:/project/baseline-project/baseline-backend/module-service/*/pom.xml
```

Expected: no output

- [ ] **Step 2: Reload Maven in IDEA**

In IDEA: Right-click on project → Maven → Reload All Projects

Expected: Red errors should disappear

---

## Self-Review Checklist

- [ ] All `com.baseline.library` → `com.baseline`
- [ ] All `cloud-common-common-biz` → `cloud-common-biz`
- [ ] Non-existent service references removed from frame-service
- [ ] No remaining `com.baseline.library` in any service pom.xml
