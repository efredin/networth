/*
 * Haven't been able to figure out how to run embeded mongoDB + mvc 
 * in order to create end-to-end integration tests
 */

// package com.efredin.networth.balancesheets;

// import static org.junit.Assert.fail;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// import org.junit.After;
// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit4.SpringRunner;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.web.context.WebApplicationContext;

// import de.flapdoodle.embed.mongo.MongodExecutable;
// import de.flapdoodle.embed.mongo.MongodStarter;
// import de.flapdoodle.embed.mongo.config.IMongodConfig;
// import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
// import de.flapdoodle.embed.mongo.config.Net;
// import de.flapdoodle.embed.mongo.distribution.Version;
// import de.flapdoodle.embed.process.runtime.Network;

// @RunWith(SpringRunner.class)
// @SpringBootTest
// // @DataMongoTest()
// public class BalanceSheetControllerIntegrationTests {

//     @Autowired
//     private WebApplicationContext context;
//     private MockMvc mockMvc;
//     private MongodExecutable mongodExecutable;
//     // private MongoTemplate mongoTemplate;

//     @Before
//     public void setup() throws Exception {
//         this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

//         String ip = "localhost";
//         int port = 27017;

//         IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
//                 .net(new Net(ip, port, Network.localhostIsIPv6())).build();
 
//         MongodStarter starter = MongodStarter.getDefaultInstance();
//         mongodExecutable = starter.prepare(mongodConfig);
//         mongodExecutable.start();
//         // this.mongoTemplate = new MongoTemplate(new MongoClient(ip, port), "test");
//     }

//     @After
//     public void clean() {
//         this.mongodExecutable.stop();
//     }

//     @Test
//     public void getSheet() {
//         String sheetId = "sheet-1234";

//         try {
//             this.mockMvc.perform(get("/" + sheetId))
//                 .andDo(print())
//                 .andExpect(jsonPath("$.id").value(sheetId));
//         } catch (Exception e) {
//             fail("Unhandled exception");
//         }
//     }
// }