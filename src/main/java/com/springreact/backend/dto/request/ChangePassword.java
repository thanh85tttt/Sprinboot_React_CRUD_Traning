package com.springreact.backend.dto.request;

/**
 * ChangePassword
 * <p>
 * Version 1.0
 * <p>
 * Date: 30-05-2021
 * <p>
 * Copyright By Thanh
 * <p>
 * Modification Logs:
 * DATE             AUTHOR              DESCRIPTION
 * -------------------------------------------------
 * 07-06-2021       ThanhBT11           Create
 */
public class ChangePassword {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    /**
     * Default constructor
     */
    public ChangePassword() {
    }

    /**
     * Change password constructor(String, String, String)
     *
     * @param oldPassword     old password of an account
     * @param newPassword     new password of an account
     * @param confirmPassword confirm password is match with new password
     */
    public ChangePassword(String oldPassword, String newPassword, String confirmPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    /**
     * Get old password
     *
     * @return old password
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * Set old password
     *
     * @param oldPassword old password of an account
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * get new password
     *
     * @return new password of an account
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Set new password
     *
     * @param newPassword new password of an account
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Get confirm password
     *
     * @return confirm password
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Set confirm password
     *
     * @param confirmPassword confirm password is match with new password or not
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * To string
     *
     * @return change password information
     */
    @Override
    public String toString() {
        return "ChangePassword{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
