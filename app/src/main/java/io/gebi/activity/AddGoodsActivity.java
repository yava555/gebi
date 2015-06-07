package io.gebi.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.gebi.APIBuilder;
import io.gebi.Commons;
import io.gebi.R;
import io.gebi.model.Goods;
import io.gebi.model.Notice;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by yava on 15/5/7.
 */
public class AddGoodsActivity extends BaseActivity {

    private static final int REQUEST_CHOOSE_IMAGE = 1;
    private Uri outputFileUri;
    private GridView mGridView;
    private ImageAdapter mImageAdapter;

    @InjectView(R.id.name_edit)
    EditText mNameEdit;
    @InjectView(R.id.description_edit)
    EditText mDescriptionEdit;
    @InjectView(R.id.price_edit)
    EditText mPriceEdit;


    public static void startForResult(Activity activity, int requestCode) {
        Intent i = new Intent(activity, AddGoodsActivity.class);
        activity.startActivityForResult(i, requestCode);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);

        ButterKnife.inject(this);

        mGridView = (GridView) findViewById(R.id.image_grid);
        mImageAdapter = new ImageAdapter(AddGoodsActivity.this);
        mGridView.setAdapter(mImageAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imagePath = (String) mImageAdapter.getItem(position);
                if (imagePath != null) {
                    showRemoveImageDialog(imagePath);
                } else {
                    openImageIntent();
                }
            }
        });

    }

    @OnClick(R.id.button)
    void onButtonClick() {
        String name = mNameEdit.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(AddGoodsActivity.this, "请填写宝贝名称！", Toast.LENGTH_LONG).show();
            return;
        }
        if (mImageAdapter.getImageCount() == 0) {
            Toast.makeText(AddGoodsActivity.this, "请至少选择一张图片！", Toast.LENGTH_LONG).show();
            return;
        }

        Goods goods = new Goods();
        goods.setName(name);
        goods.setDescription(mDescriptionEdit.getText().toString());
        goods.setPrice(mPriceEdit.getText().toString());

        showProgressDialog();
        TypedFile typedFile1 = null, typedFile2 = null, typedFile3 = null, typedFile4 = null;
        if (mImageAdapter.getItem(0) != null) {
            typedFile1 = new TypedFile("image/png", new File((String) mImageAdapter.getItem(0)));
        }
        if (mImageAdapter.getItem(1) != null) {
            typedFile2 = new TypedFile("image/png", new File((String) mImageAdapter.getItem(1)));
        }
        if (mImageAdapter.getItem(2) != null) {
            typedFile3 = new TypedFile("image/png", new File((String) mImageAdapter.getItem(2)));
        }
        if (mImageAdapter.getItem(3) != null) {
            typedFile4 = new TypedFile("image/png", new File((String) mImageAdapter.getItem(3)));
        }
        APIBuilder.create().postGoods(typedFile1, typedFile2, typedFile3, typedFile4, goods.getName(), goods.getDescription(), goods.getPrice(), new Callback<Goods>() {
            @Override
            public void success(Goods goods, Response response) {
                hiddenProgressDialog();
                Toast.makeText(AddGoodsActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                hiddenProgressDialog();
            }

        });

    }

    private void showRemoveImageDialog(final String imagePath) {

        new AlertDialog.Builder(this).setTitle("删除此照片？").setNegativeButton("取消", null).setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mImageAdapter.deleteImage(imagePath);
            }
        }).show();

    }

    private void openImageIntent() {

        File outputFile = null;
        try {
            File outputDir = getCacheDir(); // context being the Activity pointer
            outputFile = File.createTempFile("temp", "png", outputDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (outputFile == null) {
            return;
        }

        outputFileUri = Uri.fromFile(outputFile);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, REQUEST_CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_IMAGE) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                Uri selectedImageUri;
                if (isCamera) {
                    selectedImageUri = outputFileUri;
                } else {
                    selectedImageUri = data == null ? null : data.getData();
                }

                String filePath;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    filePath = getFilePathForKitKat(selectedImageUri);
                } else {
                    filePath = getFilePath(selectedImageUri);
                }
                if (filePath != null) {
                    mImageAdapter.addImage(filePath);
                }

            }
        }
    }

    private String getFilePath(Uri imageUri) {
        if (imageUri == null) {
            return null;
        }
        Cursor cursor = null;
        String filePath = null;

        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        try {
            cursor = getContentResolver().query(
                    imageUri, filePathColumn, null, null, null);
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
        } catch (Exception e) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return filePath;
    }

    private String getFilePathForKitKat(Uri imageUri) {
        // Will return "image:x*"
        if (imageUri == null) {
            return null;
        }

        String filePath = null;
        Cursor cursor = null;

        try {
            String wholeID = DocumentsContract.getDocumentId(imageUri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            cursor = getContentResolver().
                    query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, sel, new String[]{id}, null);


            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return filePath;
    }

    private class ImageAdapter extends BaseAdapter {
        private final Context context;
        private final ArrayList<String> imageList;

        public ImageAdapter(Context context) {
            this.context = context;
            this.imageList = new ArrayList<String>();
        }

        public int getImageCount() {
            return imageList.size();
        }

        @Override
        public int getCount() {
            if (imageList.size() < 4) {
                return imageList.size() + 1;
            } else {
                return imageList.size();
            }
        }

        @Override
        public Object getItem(int position) {
            if (position < imageList.size()) {
                return imageList.get(position);
            } else {
                return null;
            }
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String imagePath = (String) getItem(position);

            SimpleDraweeView imageView = new SimpleDraweeView(context);
            imageView.setLayoutParams(new AbsListView.LayoutParams(Commons.dip2px(getApplicationContext(), 60), Commons.dip2px(getApplicationContext(), 60)));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (imagePath == null) {
                imageView.setImageResource(R.drawable.ic_take_photo);
            } else {
                imageView.setImageURI(Uri.fromFile(new File(imagePath)));
            }
            return imageView;
        }

        public void addImage(String filePath) {
            imageList.add(filePath);
            notifyDataSetChanged();
        }

        public void deleteImage(String imagePath) {
            imageList.remove(imagePath);
            notifyDataSetChanged();
        }
    }
}
