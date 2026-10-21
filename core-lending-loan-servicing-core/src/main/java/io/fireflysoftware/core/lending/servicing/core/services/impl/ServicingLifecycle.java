/*
 * Copyright 2025 Firefly Software Foundation
 *
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
 */


package io.fireflysoftware.core.lending.servicing.core.services.impl;

import io.fireflysoftware.core.lending.servicing.interfaces.enums.ServicingStatusEnum;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Allowed moves for a loan servicing case. Pending is the only status that can be removed.
 */
final class ServicingLifecycle {

    private static final Map<ServicingStatusEnum, Set<ServicingStatusEnum>> ALLOWED = Map.ofEntries(
            Map.entry(ServicingStatusEnum.PENDING, EnumSet.of(
                    ServicingStatusEnum.ACTIVE, ServicingStatusEnum.CANCELLED)),
            Map.entry(ServicingStatusEnum.ACTIVE, EnumSet.of(
                    ServicingStatusEnum.GRACE_PERIOD, ServicingStatusEnum.DELINQUENT,
                    ServicingStatusEnum.FORBEARANCE, ServicingStatusEnum.RESTRUCTURED,
                    ServicingStatusEnum.BANKRUPTCY, ServicingStatusEnum.FORECLOSURE,
                    ServicingStatusEnum.PAID_OFF, ServicingStatusEnum.CLOSED,
                    ServicingStatusEnum.TRANSFERRED, ServicingStatusEnum.CANCELLED,
                    ServicingStatusEnum.DEFAULT)),
            Map.entry(ServicingStatusEnum.GRACE_PERIOD, EnumSet.of(
                    ServicingStatusEnum.ACTIVE, ServicingStatusEnum.DELINQUENT, ServicingStatusEnum.FORBEARANCE)),
            Map.entry(ServicingStatusEnum.DELINQUENT, EnumSet.of(
                    ServicingStatusEnum.ACTIVE, ServicingStatusEnum.DEFAULT,
                    ServicingStatusEnum.FORBEARANCE, ServicingStatusEnum.GRACE_PERIOD)),
            Map.entry(ServicingStatusEnum.DEFAULT, EnumSet.of(
                    ServicingStatusEnum.FORECLOSURE, ServicingStatusEnum.BANKRUPTCY,
                    ServicingStatusEnum.CHARGED_OFF, ServicingStatusEnum.FORBEARANCE,
                    ServicingStatusEnum.RESTRUCTURED)),
            Map.entry(ServicingStatusEnum.FORBEARANCE, EnumSet.of(
                    ServicingStatusEnum.ACTIVE, ServicingStatusEnum.DELINQUENT, ServicingStatusEnum.RESTRUCTURED)),
            Map.entry(ServicingStatusEnum.RESTRUCTURED, EnumSet.of(
                    ServicingStatusEnum.ACTIVE, ServicingStatusEnum.DELINQUENT)),
            Map.entry(ServicingStatusEnum.BANKRUPTCY, EnumSet.of(
                    ServicingStatusEnum.CHARGED_OFF, ServicingStatusEnum.ACTIVE, ServicingStatusEnum.CLOSED)),
            Map.entry(ServicingStatusEnum.FORECLOSURE, EnumSet.of(
                    ServicingStatusEnum.CHARGED_OFF, ServicingStatusEnum.CLOSED)),
            Map.entry(ServicingStatusEnum.CHARGED_OFF, EnumSet.of(ServicingStatusEnum.CLOSED)),
            Map.entry(ServicingStatusEnum.PAID_OFF, EnumSet.of(ServicingStatusEnum.CLOSED))
    );

    private ServicingLifecycle() {
    }

    static boolean canTransition(ServicingStatusEnum from, ServicingStatusEnum to) {
        if (from == to) {
            return true;
        }
        Set<ServicingStatusEnum> next = ALLOWED.get(from);
        return next != null && next.contains(to);
    }
}
