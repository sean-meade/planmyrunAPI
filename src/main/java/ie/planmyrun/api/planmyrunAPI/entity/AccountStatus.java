package ie.planmyrun.api.planmyrunAPI.entity;

/**
 * Account status for voice AI / support checks.
 * "Active" means the account exists and is not suspended or deleted.
 */
public enum AccountStatus {
    ACTIVE,
    SUSPENDED,
    DELETED
}
