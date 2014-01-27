/*
 * Copyright (C) 2011-2013 The Android Open Source Project
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
 * limitations under the License.
 */

/*
 * This file is auto-generated. DO NOT MODIFY!
 * The source Renderscript file: C:\\Users\\Ching-Lun\\Documents\\workplace\\MatrixComputation\\src\\com\\cllin\\matrixcomputation\\computation\\multiplication.rs
 */
package com.cllin.matrixcomputation.matrixcomputation;

import android.renderscript.*;
import android.content.res.Resources;

/**
 * @hide
 */
public class ScriptC_multiplication extends ScriptC {
    private static final String __rs_resource_name = "multiplication";
    // Constructor
    public  ScriptC_multiplication(RenderScript rs) {
        this(rs,
             rs.getApplicationContext().getResources(),
             rs.getApplicationContext().getResources().getIdentifier(
                 __rs_resource_name, "raw",
                 rs.getApplicationContext().getPackageName()));
    }

    public  ScriptC_multiplication(RenderScript rs, Resources resources, int id) {
        super(rs, resources, id);
        __ALLOCATION = Element.ALLOCATION(rs);
        __SCRIPT = Element.SCRIPT(rs);
        mExportVar_rows = 0;
        __I32 = Element.I32(rs);
        mExportVar_cols = 0;
        __F32 = Element.F32(rs);
    }

    private Element __ALLOCATION;
    private Element __F32;
    private Element __I32;
    private Element __SCRIPT;
    private FieldPacker __rs_fp_ALLOCATION;
    private FieldPacker __rs_fp_I32;
    private FieldPacker __rs_fp_SCRIPT;
    private final static int mExportVarIdx_gInputA = 0;
    private Allocation mExportVar_gInputA;
    public synchronized void set_gInputA(Allocation v) {
        setVar(mExportVarIdx_gInputA, v);
        mExportVar_gInputA = v;
    }

    public Allocation get_gInputA() {
        return mExportVar_gInputA;
    }

    private final static int mExportVarIdx_gInputB = 1;
    private Allocation mExportVar_gInputB;
    public synchronized void set_gInputB(Allocation v) {
        setVar(mExportVarIdx_gInputB, v);
        mExportVar_gInputB = v;
    }

    public Allocation get_gInputB() {
        return mExportVar_gInputB;
    }

    private final static int mExportVarIdx_gOut = 2;
    private Allocation mExportVar_gOut;
    public synchronized void set_gOut(Allocation v) {
        setVar(mExportVarIdx_gOut, v);
        mExportVar_gOut = v;
    }

    public Allocation get_gOut() {
        return mExportVar_gOut;
    }

    private final static int mExportVarIdx_gScript = 3;
    private Script mExportVar_gScript;
    public synchronized void set_gScript(Script v) {
        setVar(mExportVarIdx_gScript, v);
        mExportVar_gScript = v;
    }

    public Script get_gScript() {
        return mExportVar_gScript;
    }

    private final static int mExportVarIdx_rows = 4;
    private int mExportVar_rows;
    public synchronized void set_rows(int v) {
        setVar(mExportVarIdx_rows, v);
        mExportVar_rows = v;
    }

    public int get_rows() {
        return mExportVar_rows;
    }

    private final static int mExportVarIdx_cols = 5;
    private int mExportVar_cols;
    public synchronized void set_cols(int v) {
        setVar(mExportVarIdx_cols, v);
        mExportVar_cols = v;
    }

    public int get_cols() {
        return mExportVar_cols;
    }

    private final static int mExportForEachIdx_root = 0;
    public void forEach_root(Allocation ain, Allocation aout) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__F32)) {
            throw new RSRuntimeException("Type mismatch with F32!");
        }
        // check aout
        if (!aout.getType().getElement().isCompatible(__F32)) {
            throw new RSRuntimeException("Type mismatch with F32!");
        }
        // Verify dimensions
        Type tIn = ain.getType();
        Type tOut = aout.getType();
        if ((tIn.getCount() != tOut.getCount()) ||
            (tIn.getX() != tOut.getX()) ||
            (tIn.getY() != tOut.getY()) ||
            (tIn.getZ() != tOut.getZ()) ||
            (tIn.hasFaces() != tOut.hasFaces()) ||
            (tIn.hasMipmaps() != tOut.hasMipmaps())) {
            throw new RSRuntimeException("Dimension mismatch between input and output parameters!");
        }
        forEach(mExportForEachIdx_root, ain, aout, null);
    }

    private final static int mExportFuncIdx_compute = 0;
    public void invoke_compute() {
        invoke(mExportFuncIdx_compute);
    }

}

