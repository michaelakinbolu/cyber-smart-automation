package cyber.smart.automation.perf.stepLib;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cyber.smart.automation.utils.PropertyReader;

public class JmeterProxy {

    private final static Logger logger = LoggerFactory.getLogger(JmeterProxy.class);

    public static String getJmeterProxy() {

        String proxyAddress = "";
        try {
            System.setProperty("java.net.useSystemProxies", "true");
            List<Proxy> l = ProxySelector.getDefault().select(
                    new URI("http://" + PropertyReader.getPropValues("jmeter.jmx.endpoint") + "/"));

            for (Iterator<Proxy> iter = l.iterator(); iter.hasNext(); ) {

                Proxy proxy = iter.next();
                logger.info("proxy Type : " + proxy.type());

                InetSocketAddress addr = (InetSocketAddress) proxy.address();

                if (addr == null) {
                    logger.info("No Proxy");
                    proxyAddress = "";
                } else {
                    logger.info("proxy Hostname : " + addr.getHostName());
                    logger.info("proxy Port : " + addr.getPort());
                    proxyAddress = addr.getHostName() + ":" + addr.getPort();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proxyAddress;
    }
}
