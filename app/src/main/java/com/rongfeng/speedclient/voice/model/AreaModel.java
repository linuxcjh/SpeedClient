package com.rongfeng.speedclient.voice.model;

import java.io.Serializable;
import java.util.List;

/**
 * AUTHOR: Alex
 * DATE: 16/11/2015 10:22
 */
public class AreaModel implements Serializable{


    /**
     * areaId : 1eb11734ee564e65b79d4e953d547793
     * areaName : 山西省
     * areaType : 1
     * parentId :
     */

    private String areaId;
    private String areaName;
    private int areaType;
    private String parentId;
    private List<ShiEntity> shi;

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setAreaType(int areaType) {
        this.areaType = areaType;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setShi(List<ShiEntity> shi) {
        this.shi = shi;
    }

    public String getAreaId() {
        return areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public int getAreaType() {
        return areaType;
    }

    public String getParentId() {
        return parentId;
    }

    public List<ShiEntity> getShi() {
        return shi;
    }

    public static class ShiEntity implements Serializable{
        /**
         * areaId : 638aa068339943e6a6a85fca78af9202
         * areaName : 运城市
         * areaType : 2
         * parentId : 1eb11734ee564e65b79d4e953d547793
         */

        private String areaId;
        private String areaName;
        private int areaType;
        private String parentId;
        private List<QuEntity> qu;

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public void setAreaType(int areaType) {
            this.areaType = areaType;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public void setQu(List<QuEntity> qu) {
            this.qu = qu;
        }

        public String getAreaId() {
            return areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public int getAreaType() {
            return areaType;
        }

        public String getParentId() {
            return parentId;
        }

        public List<QuEntity> getQu() {
            return qu;
        }

        public static class QuEntity implements Serializable{
            /**
             * areaId : eb52f03561684fc88b492334c1529d3b
             * areaName : 临猗县
             * areaType : 3
             * parentId : 638aa068339943e6a6a85fca78af9202
             */

            private String areaId;
            private String areaName;
            private int areaType;
            private String parentId;

            public void setAreaId(String areaId) {
                this.areaId = areaId;
            }

            public void setAreaName(String areaName) {
                this.areaName = areaName;
            }

            public void setAreaType(int areaType) {
                this.areaType = areaType;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getAreaId() {
                return areaId;
            }

            public String getAreaName() {
                return areaName;
            }

            public int getAreaType() {
                return areaType;
            }

            public String getParentId() {
                return parentId;
            }
        }
    }
}
