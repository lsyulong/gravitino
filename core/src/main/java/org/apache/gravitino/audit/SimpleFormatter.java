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

package org.apache.gravitino.audit;

import org.apache.gravitino.audit.AuditLog.Status;
import org.apache.gravitino.listener.api.event.Event;
import org.apache.gravitino.listener.api.event.FailureEvent;

/**
 * The first version of formatter implementation of the audit log.
 *
 * @deprecated since 0.8.0, please use {@link org.apache.gravitino.audit.v2.SimpleFormatterV2}
 */
@Deprecated
public class SimpleFormatter implements Formatter {

  @Override
  @SuppressWarnings("deprecation")
  public SimpleAuditLog format(Event event) {
    Status status = event instanceof FailureEvent ? Status.FAILURE : Status.SUCCESS;
    return SimpleAuditLog.builder()
        .user(event.user())
        .operation(AuditLog.Operation.fromEvent(event))
        .identifier(event.identifier() != null ? event.identifier().toString() : null)
        .timestamp(event.eventTime())
        .status(status)
        .build();
  }
}
