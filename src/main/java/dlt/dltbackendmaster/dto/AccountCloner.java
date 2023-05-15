package dlt.dltbackendmaster.dto;

import dlt.dltbackendmaster.domain.Account;

public class AccountCloner {
	public static Account cloneAndRemoveField(Account original)
			throws CloneNotSupportedException {
		Account cloned = (Account) original.clone();
		cloned.setPassword(null);
		return cloned;
	}
}