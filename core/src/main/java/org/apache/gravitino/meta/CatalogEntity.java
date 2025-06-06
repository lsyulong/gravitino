/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.gravitino.meta;

import com.google.common.base.Objects;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.ToString;
import org.apache.gravitino.Audit;
import org.apache.gravitino.Auditable;
import org.apache.gravitino.Catalog;
import org.apache.gravitino.Entity;
import org.apache.gravitino.Field;
import org.apache.gravitino.HasIdentifier;
import org.apache.gravitino.Namespace;
import org.apache.gravitino.connector.CatalogInfo;

/** An entity within a catalog. */
@ToString
public class CatalogEntity implements Entity, Auditable, HasIdentifier {

  public static final Field ID =
      Field.required("id", Long.class, "The catalog's unique identifier");
  public static final Field NAME = Field.required("name", String.class, "The catalog's name");
  public static final Field TYPE =
      Field.required("type", Catalog.Type.class, "The type of the catalog");
  public static final Field PROVIDER =
      Field.required("provider", String.class, "The provider of the catalog");
  public static final Field COMMENT =
      Field.optional("comment", String.class, "The comment or description of the catalog");
  public static final Field PROPERTIES =
      Field.optional("properties", Map.class, "The properties associated with the catalog");
  public static final Field AUDIT_INFO =
      Field.required("audit_info", AuditInfo.class, "The audit details of the catalog");

  private Long id;

  private String name;

  @Getter private Catalog.Type type;

  @Getter private String provider;

  @Nullable @Getter private String comment;

  @Nullable @Getter private Map<String, String> properties;

  private AuditInfo auditInfo;

  private Namespace namespace;

  /**
   * A map of fields and their corresponding values.
   *
   * @return An unmodifiable map containing the entity's fields and values.
   */
  @Override
  public Map<Field, Object> fields() {
    Map<Field, Object> fields = new HashMap<>();
    fields.put(ID, id);
    fields.put(NAME, name);
    fields.put(COMMENT, comment);
    fields.put(TYPE, type);
    fields.put(PROVIDER, provider);
    fields.put(PROPERTIES, properties);
    fields.put(AUDIT_INFO, auditInfo);

    return Collections.unmodifiableMap(fields);
  }

  /**
   * The audit information of the catalog.
   *
   * @return the audit information as an {@link Audit} instance.
   */
  @Override
  public Audit auditInfo() {
    return auditInfo;
  }

  /**
   * The name of the catalog entity.
   *
   * @return The name of the catalog entity.
   */
  @Override
  public String name() {
    return name;
  }

  @Override
  public Long id() {
    return id;
  }

  /**
   * The namespace of the catalog entity.
   *
   * @return The namespace as a {@link Namespace} instance.
   */
  @Override
  public Namespace namespace() {
    return namespace;
  }

  /**
   * The type of the entity.
   *
   * @return The {@link EntityType#CATALOG} value.
   */
  @Override
  public EntityType type() {
    return EntityType.CATALOG;
  }

  /**
   * Converts the catalog entity to a {@link CatalogInfo} instance.
   *
   * @return a new {@link CatalogInfo} instance
   */
  public CatalogInfo toCatalogInfo() {
    return new CatalogInfo(id, name, type, provider, comment, properties, auditInfo, namespace);
  }

  public CatalogInfo toCatalogInfoWithoutHiddenProps(Set<String> hiddenKeys) {
    Map<String, String> filteredProperties =
        properties == null ? new HashMap<>() : new HashMap<>(properties);
    filteredProperties.keySet().removeAll(hiddenKeys);
    return new CatalogInfo(
        id, name, type, provider, comment, filteredProperties, auditInfo, namespace);
  }

  public CatalogInfo toCatalogInfoWithResolvedProps(Map<String, String> resolvedProperties) {
    return new CatalogInfo(
        id, name, type, provider, comment, resolvedProperties, auditInfo, namespace);
  }

  /** Builder class for creating instances of {@link CatalogEntity}. */
  public static class Builder {

    private final CatalogEntity catalog;

    /** Constructs a new {@link Builder}. */
    private Builder() {
      catalog = new CatalogEntity();
    }

    /**
     * Sets the unique identifier of the catalog.
     *
     * @param id the unique identifier of the catalog.
     * @return the builder instance.
     */
    public Builder withId(Long id) {
      catalog.id = id;
      return this;
    }

    /**
     * Sets the name of the catalog.
     *
     * @param name the name of the catalog.
     * @return the builder instance.
     */
    public Builder withName(String name) {
      catalog.name = name;
      return this;
    }

    /**
     * Sets the namespace of the catalog.
     *
     * @param namespace the namespace as a {@link Namespace} instance.
     * @return the builder instance.
     */
    public Builder withNamespace(Namespace namespace) {
      catalog.namespace = namespace;
      return this;
    }

    /**
     * Sets the type of the catalog.
     *
     * @param type the type of the catalog.
     * @return the builder instance.
     */
    public Builder withType(Catalog.Type type) {
      catalog.type = type;
      return this;
    }

    /**
     * Sets the provider of the catalog.
     *
     * @param provider the provider of the catalog.
     * @return the builder instance.
     */
    public Builder withProvider(String provider) {
      catalog.provider = provider;
      return this;
    }

    /**
     * Sets the comment or description of the catalog.
     *
     * @param comment the comment of the catalog.
     * @return the builder instance.
     */
    public Builder withComment(String comment) {
      catalog.comment = comment;
      return this;
    }

    /**
     * Sets the properties of the catalog.
     *
     * @param properties the properties as a map of key-value pairs.
     * @return the builder instance.
     */
    public Builder withProperties(Map<String, String> properties) {
      catalog.properties = properties;
      return this;
    }

    /**
     * Sets the audit information of the catalog.
     *
     * @param auditInfo the audit information as an {@link AuditInfo} instance.
     * @return the builder instance.
     */
    public Builder withAuditInfo(AuditInfo auditInfo) {
      catalog.auditInfo = auditInfo;
      return this;
    }

    /**
     * Builds the {@link CatalogEntity} instance after validation.
     *
     * @return the constructed and validated {@link CatalogEntity} instance.
     */
    public CatalogEntity build() {
      catalog.validate();
      return catalog;
    }
  }

  /**
   * Compares this object to the specified object for equality. Note: This method ignores comparing
   * the namespace field.
   *
   * @param o the object to compare to.
   * @return {@code true} if the objects are equal, {@code false} otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CatalogEntity)) {
      return false;
    }
    CatalogEntity that = (CatalogEntity) o;
    return Objects.equal(id, that.id)
        && Objects.equal(name, that.name)
        && Objects.equal(namespace, that.namespace)
        && type == that.type
        && Objects.equal(provider, that.provider)
        && Objects.equal(comment, that.comment)
        && Objects.equal(properties, that.properties)
        && Objects.equal(auditInfo, that.auditInfo);
  }

  /**
   * Generates a hash code value for this object based on its attributes.
   *
   * @return the hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hashCode(id, name, type, provider, comment, properties, auditInfo);
  }

  public static Builder builder() {
    return new Builder();
  }
}
