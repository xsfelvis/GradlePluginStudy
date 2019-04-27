package me.xsfdev.learntransform.buildtimecost

import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState

/**
 * Description: 统计项目中task执行时长等信息
 * <p>
 * Author xsf
 * Date 2019-04-27
 */
public class BuildTimeCostPlugin implements Plugin<Project> {
    //用来记录 task 的执行时长等信息
    Map<String, TaskExecTimeInfo> timeCostMap = new HashMap<>()
    //用来按顺序记录执行的 task 名称
    List<String> taskPathList = new ArrayList<>()


    @Override
    void apply(Project project) {

        //创建一个Extension 配置输出结果
        final BuildTimeCostExtension timeCostExtension = project.getExtensions().create("taskExecTime", BuildTimeCostExtension)

        //监听每个task的执行
        project.getGradle().addListener(new TaskExecutionListener() {
            @Override
            void beforeExecute(Task task) {
                //task开始执行前收集task的信息
                TaskExecTimeInfo timeInfo = new TaskExecTimeInfo()
                //记录开始时间
                timeInfo.start = System.currentTimeMillis()
                timeInfo.path = task.path
                timeCostMap.put(task.path, timeInfo)
                taskPathList.add(task.path)

            }

            @Override
            void afterExecute(Task task, TaskState state) {
                //task执行完成之后 记录结束时的时间
                TaskExecTimeInfo timeInfo = timeCostMap.get(task.path)
                timeInfo.end = System.currentTimeMillis()
                //计算该task的执行时长
                timeInfo.total = timeInfo.end - timeInfo.start

            }
        })

        //编译结束之后
        project.getGradle().addBuildListener(new BuildListener() {
            @Override
            void buildStarted(Gradle gradle) {

            }

            @Override
            void settingsEvaluated(Settings settings) {

            }

            @Override
            void projectsLoaded(Gradle gradle) {

            }

            @Override
            void projectsEvaluated(Gradle gradle) {

            }

            @Override
            void buildFinished(BuildResult result) {
                println "---------------------------------------"
                println "---------------------------------------"
                println "build finished, now println all task execution time:"
                if (timeCostExt.sorted) {
                    //进行排序
                    List<TaskExecTimeInfo> list = new ArrayList<>()
                    for (Map.Entry<String, TaskExecTimeInfo> entry : timeCostMap) {
                        list.add(entry.value)
                    }
                    Collections.sort(list, new Comparator<TaskExecTimeInfo>() {
                        @Override
                        int compare(TaskExecTimeInfo t1, TaskExecTimeInfo t2) {
                            return t2.total - t1.total
                        }
                    })
                    for (TaskExecTimeInfo timeInfo : list) {
                        long t = timeInfo.total
                        if (t >= timeCostExt.threshold) {
                            println("${timeInfo.path}  [${t}ms]")
                        }
                    }
                } else {
                    //按 task 执行顺序打印出执行时长信息
                    for (String path : taskPathList) {
                        long t = timeCostMap.get(path).total
                        if (t >= timeCostExt.threshold) {
                            println("${path}  [${t}ms]")
                        }
                    }
                }
                println "---------------------------------------"
                println "---------------------------------------"
            }
        })

    }

    //关于 task 的执行信息
    class TaskExecTimeInfo {

        long total      //task执行总时长

        String path
        long start      //task 执行开始时间
        long end        //task 结束时间

    }
}
