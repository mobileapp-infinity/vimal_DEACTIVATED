package com.infinity.infoway.vimal.delear.activity.PerfomInVoiceLedger;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.vimal.R;

import java.io.File;
import java.io.FileOutputStream;

public class LedgerAdapter extends RecyclerView.Adapter<LedgerAdapter.MyViewHolder> {
    private Context context;
    private Get_Account_Ledger_Of_Login_User get_account_ledger_of_login_user;
    private String extension;


    public LedgerAdapter(Context context, Get_Account_Ledger_Of_Login_User get_account_ledger_of_login_user) {
        this.context = context;
        this.get_account_ledger_of_login_user = get_account_ledger_of_login_user;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.perform_invoice_list_view,parent,false);

       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.tv_invoice_name.setText(get_account_ledger_of_login_user.getRECORDS().get(position).getAcc_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String baser64String = get_account_ledger_of_login_user.getRECORDS().get(position).getFILE();
                try {


                    File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/LedgerPDF/");
                    folder.mkdirs();


                    File file = new File(folder, get_account_ledger_of_login_user.getRECORDS().get(position).getAcc_name()+".pdf");
                    file.createNewFile();
                    FileOutputStream output = new FileOutputStream(file);
                    extension = file.toString().substring(file.toString().lastIndexOf("."), file.toString().length());
                    System.out.println("hi" + extension);
                    byte[] pdfAsBytes = Base64.decode(baser64String, Base64.NO_WRAP);
                    output.write(pdfAsBytes);
                    output.flush();
                    // closing streams
                    output.close();//have run ka

                    //open file/////
                    File file1 = new File(file.getAbsolutePath());
                    System.out.println(file1);
                    Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file1);

                    context.grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Intent target = new Intent(Intent.ACTION_VIEW);
                    target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    Intent intent = null;
                    if (extension.compareToIgnoreCase(".pdf") == 0 || extension.compareToIgnoreCase("pdf") == 0) {
                        target.setDataAndType(uri, "application/pdf");
                    }
                    intent = Intent.createChooser(target, "Open File");
                    try {
                        context.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        // DialogUtils.Show_Toast(ctx, "No Apps can performs This action");
                    }



                    //open file/////


//                            final File dwldsPath = new File("testing_pdf_file" + ".pdf");
//                            String filePath = PerFormInvoiceAndLedgerActivity.this.getFilesDir().getPath().toString() + dwldsPath;
//
//                            File f = new File(filePath);
//                            byte[] pdfAsBytes = Base64.decode(baser64String, Base64.NO_WRAP);
//                            FileOutputStream os;
//                            os = new FileOutputStream(dwldsPath, false);
//                            os.write(pdfAsBytes);
//                            os.flush();
//                            os.close();

//                             FileOutputStream fos = new FileOutputStream(dwldsPath);
//                             fos.write(Base64.decode(base64String, Base64.NO_WRAP));
//                             fos.close();//try karo run kari n
                    //exception awe che read only filesystem and file save nathi thati


                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    Toast.makeText(context, "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return get_account_ledger_of_login_user.getRECORDS().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_invoice_name;
        ImageView img_download_pdf;
        public MyViewHolder(View itemView) {
            super(itemView);

            tv_invoice_name = itemView.findViewById(R.id.tv_invoice_name);
            img_download_pdf = itemView.findViewById(R.id.img_download_pdf);
        }
    }
}
