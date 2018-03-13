package com.adaps.ain043.samples.miracast.utils;

import android.util.Log;

public class ExecTerminal {
    final String TAG = getClass().getName();

    public class ExecResult {
        final String TAG = getClass().getName();
        String stdErr = "";
        String stdIn = "";
        String stdOut = "";

        public ExecResult(String stdIn, String stdOut, String stdErr) {
            this.stdOut = stdOut;
            this.stdErr = stdErr;
            this.stdIn = stdIn;
        }

        public ExecResult() {
            clear();
        }

        public void clear() {
            this.stdOut = "";
            this.stdErr = "";
            this.stdIn = "";
        }

        public String getStdOut() {
            return this.stdOut;
        }

        public String getStdErr() {
            return this.stdErr;
        }

        public String getStdIn() {
            return this.stdIn;
        }
    }

    public boolean checkSu() {
        if (new ExecTerminal().execSu("su && echo 1").getStdOut().trim().equals("1")) {
            Log.i(this.TAG, "^ got root!");
            return true;
        }
        Log.w(this.TAG, "^ could not get root.");
        return false;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ExecTerminal.ExecResult exec(String r13) {
        /*
        r12 = this;
        r9 = "";
        r8 = "";
        r10 = java.lang.Runtime.getRuntime();	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r11 = "sh";
        r4 = r10.exec(r11);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r1 = new java.io.DataInputStream;	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10 = r4.getInputStream();	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r1.<init>(r10);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r0 = new java.io.DataInputStream;	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10 = r4.getErrorStream();	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r0.<init>(r10);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r3 = new java.io.DataOutputStream;	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10 = r4.getOutputStream();	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r3.<init>(r10);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10.<init>();	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10 = r10.append(r13);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r11 = "\n";
        r10 = r10.append(r11);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10 = r10.toString();	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r3.writeBytes(r10);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10 = "exit\n";
        r3.writeBytes(r10);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r3.flush();	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r3.close();	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r6 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10.<init>(r1);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r6.<init>(r10);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r5 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10.<init>(r0);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r5.<init>(r10);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r4.waitFor();	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r2 = "";
    L_0x0063:
        r2 = r6.readLine();	 Catch:{ IOException -> 0x00b3, InterruptedException -> 0x00ad }
        if (r2 == 0) goto L_0x0081;
    L_0x0069:
        r10 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00b3, InterruptedException -> 0x00ad }
        r10.<init>();	 Catch:{ IOException -> 0x00b3, InterruptedException -> 0x00ad }
        r10 = r10.append(r9);	 Catch:{ IOException -> 0x00b3, InterruptedException -> 0x00ad }
        r10 = r10.append(r2);	 Catch:{ IOException -> 0x00b3, InterruptedException -> 0x00ad }
        r11 = "\n";
        r10 = r10.append(r11);	 Catch:{ IOException -> 0x00b3, InterruptedException -> 0x00ad }
        r9 = r10.toString();	 Catch:{ IOException -> 0x00b3, InterruptedException -> 0x00ad }
        goto L_0x0063;
    L_0x0081:
        r6.close();	 Catch:{ IOException -> 0x00b3, InterruptedException -> 0x00ad }
    L_0x0084:
        r2 = "";
    L_0x0086:
        r2 = r5.readLine();	 Catch:{ IOException -> 0x00b1, InterruptedException -> 0x00ad }
        if (r2 == 0) goto L_0x00a4;
    L_0x008c:
        r10 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00b1, InterruptedException -> 0x00ad }
        r10.<init>();	 Catch:{ IOException -> 0x00b1, InterruptedException -> 0x00ad }
        r10 = r10.append(r8);	 Catch:{ IOException -> 0x00b1, InterruptedException -> 0x00ad }
        r10 = r10.append(r2);	 Catch:{ IOException -> 0x00b1, InterruptedException -> 0x00ad }
        r11 = "\n";
        r10 = r10.append(r11);	 Catch:{ IOException -> 0x00b1, InterruptedException -> 0x00ad }
        r8 = r10.toString();	 Catch:{ IOException -> 0x00b1, InterruptedException -> 0x00ad }
        goto L_0x0086;
    L_0x00a4:
        r5.close();	 Catch:{ IOException -> 0x00b1, InterruptedException -> 0x00ad }
    L_0x00a7:
        r7 = new com.phongphan.utils.ExecTerminal$ExecResult;
        r7.<init>(r13, r9, r8);
        return r7;
    L_0x00ad:
        r10 = move-exception;
        goto L_0x00a7;
    L_0x00af:
        r10 = move-exception;
        goto L_0x00a7;
    L_0x00b1:
        r10 = move-exception;
        goto L_0x00a7;
    L_0x00b3:
        r10 = move-exception;
        goto L_0x0084;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.phongphan.utils.ExecTerminal.exec(java.lang.String):com.phongphan.utils.ExecTerminal$ExecResult");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ExecTerminal.ExecResult execSu(String r13) {
        /*
        r12 = this;
        r9 = "";
        r8 = "";
        r10 = java.lang.Runtime.getRuntime();	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r11 = "su";
        r4 = r10.exec(r11);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r1 = new java.io.DataInputStream;	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10 = r4.getInputStream();	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r1.<init>(r10);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r0 = new java.io.DataInputStream;	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10 = r4.getErrorStream();	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r0.<init>(r10);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r3 = new java.io.DataOutputStream;	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10 = r4.getOutputStream();	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r3.<init>(r10);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10.<init>();	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10 = r10.append(r13);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r11 = "\n";
        r10 = r10.append(r11);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10 = r10.toString();	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r3.writeBytes(r10);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10 = "exit\n";
        r3.writeBytes(r10);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r3.flush();	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r3.close();	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r6 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10.<init>(r1);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r6.<init>(r10);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r5 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r10.<init>(r0);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r5.<init>(r10);	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r4.waitFor();	 Catch:{ IOException -> 0x00af, InterruptedException -> 0x00ad }
        r2 = "";
    L_0x0063:
        r2 = r6.readLine();	 Catch:{ IOException -> 0x00b3, InterruptedException -> 0x00ad }
        if (r2 == 0) goto L_0x0081;
    L_0x0069:
        r10 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00b3, InterruptedException -> 0x00ad }
        r10.<init>();	 Catch:{ IOException -> 0x00b3, InterruptedException -> 0x00ad }
        r10 = r10.append(r9);	 Catch:{ IOException -> 0x00b3, InterruptedException -> 0x00ad }
        r10 = r10.append(r2);	 Catch:{ IOException -> 0x00b3, InterruptedException -> 0x00ad }
        r11 = "\n";
        r10 = r10.append(r11);	 Catch:{ IOException -> 0x00b3, InterruptedException -> 0x00ad }
        r9 = r10.toString();	 Catch:{ IOException -> 0x00b3, InterruptedException -> 0x00ad }
        goto L_0x0063;
    L_0x0081:
        r6.close();	 Catch:{ IOException -> 0x00b3, InterruptedException -> 0x00ad }
    L_0x0084:
        r2 = "";
    L_0x0086:
        r2 = r5.readLine();	 Catch:{ IOException -> 0x00b1, InterruptedException -> 0x00ad }
        if (r2 == 0) goto L_0x00a4;
    L_0x008c:
        r10 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00b1, InterruptedException -> 0x00ad }
        r10.<init>();	 Catch:{ IOException -> 0x00b1, InterruptedException -> 0x00ad }
        r10 = r10.append(r8);	 Catch:{ IOException -> 0x00b1, InterruptedException -> 0x00ad }
        r10 = r10.append(r2);	 Catch:{ IOException -> 0x00b1, InterruptedException -> 0x00ad }
        r11 = "\n";
        r10 = r10.append(r11);	 Catch:{ IOException -> 0x00b1, InterruptedException -> 0x00ad }
        r8 = r10.toString();	 Catch:{ IOException -> 0x00b1, InterruptedException -> 0x00ad }
        goto L_0x0086;
    L_0x00a4:
        r5.close();	 Catch:{ IOException -> 0x00b1, InterruptedException -> 0x00ad }
    L_0x00a7:
        r7 = new com.phongphan.utils.ExecTerminal$ExecResult;
        r7.<init>(r13, r9, r8);
        return r7;
    L_0x00ad:
        r10 = move-exception;
        goto L_0x00a7;
    L_0x00af:
        r10 = move-exception;
        goto L_0x00a7;
    L_0x00b1:
        r10 = move-exception;
        goto L_0x00a7;
    L_0x00b3:
        r10 = move-exception;
        goto L_0x0084;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.phongphan.utils.ExecTerminal.execSu(java.lang.String):com.phongphan.utils.ExecTerminal$ExecResult");
    }
}
