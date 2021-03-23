package openyabsx2

class ConfigEntry {

    User user
    String name
    String value

    static constraints = {
        name unique: false
        value nullable: true
        user nullable: true
    }

    static mapping = {
        value type: "text"
        cache true
    }

    static ConfigEntry getValueForUserOrCreateGlobal(String key, User user, String defaultValue) {
        ConfigEntry.withNewTransaction {
            def userOrGlobal = getValueForUser(key, user)
            if (userOrGlobal == null) {
                //create global value instead of user value
                return setForUser(key, null, defaultValue)
            } else {
                return userOrGlobal
            }
        }
    }

    static ConfigEntry getValueForUser(String key, User user1) {
        def data = ConfigEntry.findAllByName(key);
        if (!data) return null;
        ConfigEntry global = data.find { it.user == null }
        if (!user1) return global
        ConfigEntry forUser = data.find { it.user?.id == user1?.id };
        return forUser ?: global
    }

    static ConfigEntry setForUser(String key, User user, String value) {
        ConfigEntry exists = getValueForUser(key, user)

        if (!exists) {
            //new value, user or global
            def data = new ConfigEntry(name: key, value: value, user: user)
            return data.save(flush: true)
        } else if (exists.user == null && user != null) {
            // global exists, new user value
            def data = new ConfigEntry(name: key, value: value, user: user)
            return data.save(flush: true)
        } else if (exists.user == null && user == null) {
            // global value update
            exists.setValue(value)
            return exists.save(flush: true)
        } else if (exists.user != null && user != null) {
            // user value update
            exists.setValue(value)
            return exists.save(flush: true)
        } else if (exists.user != null && user == null) {
            throw new RuntimeException(key)
        }
    }
}
