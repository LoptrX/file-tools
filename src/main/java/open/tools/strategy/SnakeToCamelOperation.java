package open.tools.strategy;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SnakeToCamelOperation implements Operation {
    public final static Pattern SNAKE_PATTERN = Pattern.compile("((([a-z]+)+(_))+([a-z]+))");

    @Override
    public String doOperate(String data) {
        if (StringUtils.isBlank(data)) {
            return "";
        }

        int length = data.length();
        StringBuilder sb = new StringBuilder(length);

        Matcher matcher = SNAKE_PATTERN.matcher(data);

        int index = 0;
        while (matcher.find()) {
            int start = matcher.start();
            if (start - 1 > 0) {
                sb.append(data, index,  start - 1);
            }
            sb.append(capitalize(matcher.group()));
            index = matcher.end();
        }

        if (index != length) {
            sb.append(data, index, length);
        }

        return sb.toString();
    }

    private String capitalize(String letter) {
        String[] strings = letter.split("_");

        if (strings.length <= 1) {
            return letter;
        }

        return strings[0] + Arrays.stream(strings)
                .skip(1)
                .map(StringUtils::capitalize)
                .collect(Collectors.joining());
    }
}
