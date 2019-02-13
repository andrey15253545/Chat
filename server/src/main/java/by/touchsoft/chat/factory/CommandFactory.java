package by.touchsoft.chat.factory;

import by.touchsoft.chat.command.Command;
import by.touchsoft.chat.model.Role;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class CommandFactory {

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
            String firstWord = url.startsWith("/") ? getFirstWord(url) : null;
            return firstWord != null ? 31 * firstWord.hashCode() : 0;
        }
    }

    private Map<Key, Command> commands = new HashMap<>();


    //TODO : check key
    @Autowired
    public CommandFactory(ApplicationContext appContext) {
        Reflections reflections = new Reflections("by.touchsoft.chat.command.impl");
        Set<Class<?>> commands = reflections.getTypesAnnotatedWith(by.touchsoft.chat.annotation.Key.class);
        for (Class<?> commandClass : commands) {
            by.touchsoft.chat.annotation.Key annotation = commandClass.getAnnotation(by.touchsoft.chat.annotation.Key.class);
            String name = commandClass.getSimpleName();
            name = Character.toLowerCase(name.charAt(0))+name.substring(1);
            Command commandObj = (Command) appContext.getBean(name);
            this.commands.put(new Key(annotation.url(), annotation.role()), commandObj);
        }
    }

    public Command getCommand(String url, Role role) {
        return commands.get(new Key(url, role));
    }
}
