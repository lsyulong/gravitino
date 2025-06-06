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

package org.apache.gravitino.listener.api.event;

import org.apache.gravitino.NameIdentifier;
import org.apache.gravitino.annotation.DeveloperApi;

/**
 * Represents an event triggered when a group operation fails due to an exception. This event
 * encapsulates the group operation's unique identifier, the initiating user, and the exception that
 * led to the failure.
 */
@DeveloperApi
public abstract class GroupFailureEvent extends FailureEvent {

  /**
   * Creates a new instance of {@code GroupFailureEvent}.
   *
   * @param initiator the user who initiated the operation
   * @param identifier the unique identifier for the group operation
   * @param exception the exception encountered during the operation, detailing the cause of failure
   */
  protected GroupFailureEvent(String initiator, NameIdentifier identifier, Exception exception) {
    super(initiator, identifier, exception);
  }
}
