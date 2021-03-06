/*
 * Twittnuker - Twitter client for Android
 *
 * Copyright (C) 2013-2016 vanita5 <mail@vanit.as>
 *
 * This program incorporates a modified version of Twidere.
 * Copyright (C) 2012-2016 Mariotaku Lee <mariotaku.lee@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.vanita5.twittnuker.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import de.vanita5.twittnuker.R;
import de.vanita5.twittnuker.fragment.DataExportImportTypeSelectorDialogFragment;
import de.vanita5.twittnuker.fragment.ProgressDialogFragment;
import de.vanita5.twittnuker.util.DataImportExportUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataExportActivity extends BaseActivity implements
        DataExportImportTypeSelectorDialogFragment.Callback {

    private ExportSettingsTask mTask;
    private Runnable mResumeFragmentsRunnable;

    @Override
    public void onCancelled(DialogFragment df) {
        if (!isFinishing()) {
            finish();
        }
    }

    @Override
    public void onDismissed(final DialogFragment df) {
        if (df instanceof DataExportImportTypeSelectorDialogFragment) {
            finish();
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mResumeFragmentsRunnable != null) {
            mResumeFragmentsRunnable.run();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case REQUEST_PICK_DIRECTORY: {
                mResumeFragmentsRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (resultCode == RESULT_OK) {
                            final String path = data.getData().getPath();
                            final DialogFragment df = new DataExportImportTypeSelectorDialogFragment();
                            final Bundle args = new Bundle();
                            args.putString(EXTRA_PATH, path);
                            args.putString(EXTRA_TITLE, getString(R.string.export_settings_type_dialog_title));
                            df.setArguments(args);
                            df.show(getSupportFragmentManager(), "select_export_type");
                        } else {
                            if (!isFinishing()) {
                                finish();
                            }
                        }
                    }
                };
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPositiveButtonClicked(final String path, final int flags) {
        if (path == null || flags == 0) {
            finish();
            return;
        }
        if (mTask == null || mTask.getStatus() != AsyncTask.Status.RUNNING) {
            mTask = new ExportSettingsTask(this, path, flags);
            mTask.execute();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setVisible(true);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            final Intent intent = new Intent(this, FileSelectorActivity.class);
            intent.setAction(INTENT_ACTION_PICK_DIRECTORY);
            startActivityForResult(intent, REQUEST_PICK_DIRECTORY);
        }
    }

    static class ExportSettingsTask extends AsyncTask<Object, Object, Boolean> {
        private static final String FRAGMENT_TAG = "import_settings_dialog";

        private final DataExportActivity mActivity;
        private final String mPath;
        private final int mFlags;

        ExportSettingsTask(final DataExportActivity activity, final String path, final int flags) {
            mActivity = activity;
            mPath = path;
            mFlags = flags;
        }

        @Override
        protected Boolean doInBackground(final Object... params) {
            if (mPath == null) return false;
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
            final String fileName = String.format("Twittnuker_Settings_%s.zip", sdf.format(new Date()));
            final File file = new File(mPath, fileName);
            file.delete();
            try {
                DataImportExportUtils.exportData(mActivity, file, mFlags);
                return true;
            } catch (final IOException e) {
                Log.w(LOGTAG, e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            final FragmentManager fm = mActivity.getSupportFragmentManager();
            final DialogFragment f = (DialogFragment) fm.findFragmentByTag(FRAGMENT_TAG);
            if (f != null) {
                f.dismiss();
            }
            if (result != null && result) {
                mActivity.setResult(RESULT_OK);
            } else {
                mActivity.setResult(RESULT_CANCELED);
            }
            mActivity.finish();
        }

        @Override
        protected void onPreExecute() {
            ProgressDialogFragment.show(mActivity, FRAGMENT_TAG).setCancelable(false);
        }

    }
}