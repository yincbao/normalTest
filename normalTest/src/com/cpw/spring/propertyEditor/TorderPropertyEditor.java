package com.cpw.spring.propertyEditor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyEditorSupport;

public class TorderPropertyEditor extends PropertyEditorSupport {
	private PropertyChangeSupport support;
	// Basic Infos
	private Integer isPaid;
	private String paymentDate;
	private String screenDate;
	private String chargeBackDate;
	private String refundDate;
	private String pendingDate;
	private Integer isaskRefund;
	private Integer processRefund;
	private Integer isDelivered;
	private String deliveredDate;
	private Integer isUrgency;
	private Short urDegree;
	private Integer isinfoFull;
	private Integer isCancel;

	// Paypal Infos
	private String paymentMethod;
	private String account;
	private String paymentId;
	private String paypalEmail;
	private String paypalAddress;
	private String paypalName;
	private Float paymentAmount;
	private String bankAddr;
	private String westAddr;

	public TorderPropertyEditor() {
		super();
		this.support = new PropertyChangeSupport(this);
	}

	public TorderPropertyEditor(Object clazz) {
		super(clazz);
		this.support = new PropertyChangeSupport(this);
	}

	@Override
	public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	@Override
	public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		support.firePropertyChange("account", this.account, account);
		this.account = account;
	}

	public String getBankAddr() {
		return bankAddr;
	}

	public void setBankAddr(String bankAddr) {
		support.firePropertyChange("bankAddr", this.bankAddr, bankAddr);
		this.bankAddr = bankAddr;
	}

	public String getChargeBackDate() {
		return chargeBackDate;
	}

	public void setChargeBackDate(String chargeBackDate) {
		support.firePropertyChange("chargeBackDate", this.chargeBackDate, chargeBackDate);
		this.chargeBackDate = chargeBackDate;
	}

	public String getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(String deliveredDate) {
		support.firePropertyChange("deliveredDate", this.deliveredDate, deliveredDate);
		this.deliveredDate = deliveredDate;
	}

	public Integer getIsaskRefund() {
		return isaskRefund;
	}

	public void setIsaskRefund(Integer isaskRefund) {
		support.firePropertyChange("isaskRefund", this.isaskRefund, isaskRefund);
		this.isaskRefund = isaskRefund;
	}

	public Integer getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(Integer isCancel) {
		support.firePropertyChange("isCancel", this.isCancel, isCancel);
		this.isCancel = isCancel;
	}

	public Integer getIsDelivered() {
		return isDelivered;
	}

	public void setIsDelivered(Integer isDelivered) {
		support.firePropertyChange("isDelivered", this.isDelivered, isDelivered);
		this.isDelivered = isDelivered;
	}

	public Integer getIsinfoFull() {
		return isinfoFull;
	}

	public void setIsinfoFull(Integer isinfoFull) {
		support.firePropertyChange("isinfoFull", this.isinfoFull, isinfoFull);
		this.isinfoFull = isinfoFull;
	}

	public Integer getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Integer isPaid) {
		support.firePropertyChange("isPaid", this.isPaid, isPaid);
		this.isPaid = isPaid;
	}

	public Integer getIsUrgency() {
		return isUrgency;
	}

	public void setIsUrgency(Integer isUrgency) {
		support.firePropertyChange("isUrgency", this.isUrgency, isUrgency);
		this.isUrgency = isUrgency;
	}

	public Float getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Float paymentAmount) {
		support.firePropertyChange("paymentAmount", this.paymentAmount, paymentAmount);
		this.paymentAmount = paymentAmount;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		support.firePropertyChange("paymentDate", this.paymentDate, paymentDate);
		this.paymentDate = paymentDate;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		support.firePropertyChange("paymentId", this.paymentId, paymentId);
		this.paymentId = paymentId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		support.firePropertyChange("paymentMethod", this.paymentMethod, paymentMethod);
		this.paymentMethod = paymentMethod;
	}

	public String getPaypalAddress() {
		return paypalAddress;
	}

	public void setPaypalAddress(String paypalAddress) {
		support.firePropertyChange("paypalAddress", this.paypalAddress, paypalAddress);
		this.paypalAddress = paypalAddress;
	}

	public String getPaypalEmail() {
		return paypalEmail;
	}

	public void setPaypalEmail(String paypalEmail) {
		support.firePropertyChange("paypalEmail", this.paypalEmail, paypalEmail);
		this.paypalEmail = paypalEmail;
	}

	public String getPaypalName() {
		return paypalName;
	}

	public void setPaypalName(String paypalName) {
		support.firePropertyChange("paypalName", this.paypalName, paypalName);
		this.paypalName = paypalName;
	}

	public String getPendingDate() {
		return pendingDate;
	}

	public void setPendingDate(String pendingDate) {
		support.firePropertyChange("pendingDate", this.pendingDate, pendingDate);
		this.pendingDate = pendingDate;
	}

	public Integer getProcessRefund() {
		return processRefund;
	}

	public void setProcessRefund(Integer processRefund) {
		support.firePropertyChange("processRefund", this.processRefund, processRefund);
		this.processRefund = processRefund;
	}

	public PropertyChangeSupport getSupport() {
		return support;
	}

	public String getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(String refundDate) {
		support.firePropertyChange("refundDate", this.refundDate, refundDate);
		this.refundDate = refundDate;
	}

	public String getScreenDate() {
		return screenDate;
	}

	public void setScreenDate(String screenDate) {
		support.firePropertyChange("screenDate", this.screenDate, screenDate);
		this.screenDate = screenDate;
	}

	public Short getUrDegree() {
		return urDegree;
	}

	public void setUrDegree(Short urDegree) {
		support.firePropertyChange("urDegree", this.urDegree, urDegree);
		this.urDegree = urDegree;
	}

	public String getWestAddr() {
		return westAddr;
	}

	public void setWestAddr(String westAddr) {
		support.firePropertyChange("westAddr", this.westAddr, westAddr);
		this.westAddr = westAddr;
	}

	public static void main(String[] args) {
		TorderPropertyEditor editor = new TorderPropertyEditor();
		editor.setIsPaid(0);
		editor.setAccount("gamesavor@gmail.com");
		editor.setBankAddr("HongKong");
		editor.addPropertyChangeListener(new TorderPropertyChangeListener());
		editor.setIsPaid(1);
		editor.setAccount("111@gmail.com");
		editor.setBankAddr("German");
		PropertyChangeSupport support = editor.getSupport();
		TorderPropertyChangeListener listener = (TorderPropertyChangeListener) support.getPropertyChangeListeners()[0];
		String properties = listener.getProperties();
		System.out.println(properties);
	}
}
