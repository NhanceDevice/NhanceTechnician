/*
 * Copyright © EasOfTech 2015. All rights reserved.
 *
 * This software is the confidential and proprietary information
 *  of EasOfTech. You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms and
 *  conditions entered into with EasOfTech.
 *
 */
package com.nhance.technician.database;


import java.util.List;


/*
*
* Id: AppDBUpgradeStatements
*
* Date Author Changes
* 23-Dec-15 afsar Created
*/


public class AppDBUpgradeStatements {

    //TODO: Note:-

    public static List<DatabaseBaseModel> getStatements(int versionCode) {

        List<DatabaseBaseModel> statements = null;
        switch (versionCode) {


            default:
                break;
        }

        return statements;
    }

}
