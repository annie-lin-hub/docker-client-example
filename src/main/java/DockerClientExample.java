import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.PortBinding;

public class DockerClientExample {

	public static void main(String args[]) throws Exception {
		String dockerImageName = "nginx:latest";
		
		//Part1
		final DockerClient docker = DefaultDockerClient.fromEnv().build();
		docker.pull(dockerImageName);
		
		//Part2
		final Map<String, List<PortBinding>> portBindings = new HashMap<>();
		final HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();
		List<PortBinding> hostPorts = new ArrayList<>();
		hostPorts.add(PortBinding.of("0.0.0.0", "8080"));
		portBindings.put("80", hostPorts);
		
		final ContainerConfig containerConfig = ContainerConfig.builder()
			    .hostConfig(hostConfig)
			    .image(dockerImageName)
			    .build();
			    
		final ContainerCreation creation = docker.createContainer(containerConfig);
		
		//Part3
		final String id = creation.id();
		docker.startContainer(id);
		
	}
	
}
