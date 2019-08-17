package org.ferris.tweial.console.preferences;

/**
 * This class is a bean which holds user preferences.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class Preferences {
    protected Integer sendOnlyIfTweetsGreaterThan;
    protected Long getOnlyThisTweetId;
    protected String noImageUrl;

    public Preferences () {
    }

    public Long getGetOnlyThisTweetId() {
        return getOnlyThisTweetId;
    }

    public void setGetOnlyThisTweetId(Long getOnlyThisTweetId) {
        this.getOnlyThisTweetId = getOnlyThisTweetId;
    }

    public Boolean hasSendOnlyIfTweetsGreaterThan() {
        return sendOnlyIfTweetsGreaterThan != null;
    }
    public Integer getSendOnlyIfTweetsGreaterThan() {
        return sendOnlyIfTweetsGreaterThan;
    }

    public void setSendOnlyIfTweetsGreaterThan(Integer sendOnlyIfTweetsGreaterThan) {
        this.sendOnlyIfTweetsGreaterThan = sendOnlyIfTweetsGreaterThan;
    }

    public String getNoImageUrl() {
        return noImageUrl;
    }

    public void setNoImageUrl(String noImageUrl) {
        this.noImageUrl = noImageUrl;
    }
}
