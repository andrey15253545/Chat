package server.factory;

import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import server.command.Command;
import server.dispather.ConsoleRequestDispatcher;
import server.user.Role;

import java.lang.reflect.InvocationTargetException;
import java.util.*;


public enum CommandFactory {

    INSTANCE;

    private final Logger logger = Logger.getLogger(ConsoleRequestDispatcher.class);

    private class Key {

        private String url;
        private List<Role> role = new ArrayList<>();

        Key(String url, Role[] roles) {
            this.url = url;
            this.role.addAll(Arrays.asList(roles));
        }

        Key(String url, Role role) {
            this.url = url;
            this.role.add(role);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return (url != null ? this.url.matches(key.url) : key.url == null) && key.role.containsAll(role);
        }

        private String getFirstWord(String url){
            return url.contains(" ") ? url.substring(0,url.indexOf(" ")) : url;
        }

        @Override
        public int hashCode() {
            String firstWord = url != null ? getFirstWord(url): null;
            return url != null ? 31 * firstWord.hashCode() : 0;
        }
    }

    private Map<Key, Command> commands = new HashMap<>();

    CommandFactory() {
        Reflections reflections = new Reflections("server.command.impl");
        Set<Class<?>> commands = reflections.getTypesAnnotatedWith(server.annotation.Key.class);
        for (Class<?> commandClass : commands) {
            server.annotation.Key annotation = commandClass.getAnnotation(server.annotation.Key.class);
            try {
                Command commandObj = (Command) commandClass.getDeclaredConstructor().newInstance();
                this.commands.put(new Key(annotation.url(), annotation.role()), commandObj);
            } catch (IllegalAccessException | InstantiationException |
                    InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                logger.warn(e);
            }
        }
    }

    public Command getCommand(String url, Role role) {
        if (url.charAt(0)!='/')
            url = "/msg";
        return commands.get(new Key(url, role));
    }
}
