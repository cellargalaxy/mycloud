package top.cellargalaxy.mycloud;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.service.OwnService;
import top.cellargalaxy.mycloud.util.IOUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MycloudApplicationTests {
	@Autowired
	private OwnService ownService;

	@Test
	public void test() throws Exception {
	}

	private static void read() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		File file = new File("d:/baifen.json");

		if (!file.exists()) {
			return;
		}
		LinkedList<OwnBo> ownBos=null;
		try (BufferedReader reader = IOUtil.getReader(file)) {
			StringBuilder stringBuilder = new StringBuilder();
			String string;
			if ((string = reader.readLine()) != null) {
				stringBuilder.append(string);
			}
			ownBos= new LinkedList<>(Arrays.asList(objectMapper.readValue(stringBuilder.toString(), OwnBo[].class)));
		}
		Collections.reverse(ownBos);

		try (Writer writer = IOUtil.getWriter(file)) {
			writer.write(objectMapper.writeValueAsString(ownBos));
		}
	}

	private void write(OwnService ownService) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		File file = new File("d:/baifen.json");

		List<OwnBo> list = ownService.listAllOwn();
		LinkedList<OwnBo> ownBos = new LinkedList<>(list);
		Collections.reverse(ownBos);

		try (Writer writer = IOUtil.getWriter(file)) {
			writer.write(objectMapper.writeValueAsString(ownBos));
		}
	}
}
