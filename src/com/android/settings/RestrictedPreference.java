/*
 * Copyright (C) 2015 The Android Open Source Project
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
 * limitations under the License
 */

package com.android.settings;

import android.content.ComponentName;
import android.content.Context;
import android.os.UserHandle;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;

/**
 * Preference class that supports being disabled by a user restriction
 * set by a device admin.
 */
public class RestrictedPreference extends Preference {
    RestrictedPreferenceHelper mHelper;

    public RestrictedPreference(Context context, AttributeSet attrs,
            int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mHelper = new RestrictedPreferenceHelper(context, this, attrs);
    }

    public RestrictedPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RestrictedPreference(Context context, AttributeSet attrs) {
        this(context, attrs, TypedArrayUtils.getAttr(context, R.attr.preferenceStyle,
                android.R.attr.preferenceStyle));
    }

    public RestrictedPreference(Context context) {
        this(context, null);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        mHelper.onBindViewHolder(holder);
    }

    @Override
    public void performClick() {
        if (!mHelper.performClick()) {
            super.performClick();
        }
    }

    @Override
    protected void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        mHelper.onAttachedToHierarchy();
        super.onAttachedToHierarchy(preferenceManager);
    }

    public void checkRestrictionAndSetDisabled(String userRestriction) {
        mHelper.checkRestrictionAndSetDisabled(userRestriction, UserHandle.myUserId());
    }

    public void checkRestrictionAndSetDisabled(String userRestriction, int userId) {
        mHelper.checkRestrictionAndSetDisabled(userRestriction, userId);
    }

    public void setDisabledByAdmin(boolean disabled) {
        mHelper.setDisabledByAdmin(disabled, null, UserHandle.USER_NULL);
    }

    public void setDisabledByAdmin(boolean disabled, ComponentName admin, int userId) {
        if (mHelper.setDisabledByAdmin(disabled, admin, userId)) {
            notifyChanged();
        }
    }

    public boolean isDisabledByAdmin() {
        return mHelper.isDisabledByAdmin();
    }
}
